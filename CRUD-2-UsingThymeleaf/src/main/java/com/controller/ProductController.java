package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.model.Product;
import com.model.ProductDto;
import com.repository.ProductRepository;
import com.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	ProductRepository repo;

	@GetMapping({ "", "/" })
	public String showProductList(Model model) {
		List<Product> products = service.findAll();
		model.addAttribute("products", products);
		return "products/product_details";
	}

	@GetMapping("/index")
	public String showProductList2(Model model) {
		List<Product> products = service.findAll();
		model.addAttribute("products", products);
		return "products/index";
	}

//	@GetMapping("/create")
//	public String showCreatePage(Model model) {
//		ProductDto productdto = new ProductDto();
//		model.addAttribute("productDto", productdto);
//		return "products/CreateProduct";
//	}
//
//	@PostMapping("/create")
//	public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
//		if (productDto.getImageFile().isEmpty()) {
//			result.addError(new FieldError("productDto", "imageFile", "the image file is required"));
//		}
//
//		if (result.hasErrors()) {
//			return "products/CreateProduct";
//		}
//		MultipartFile image = productDto.getImageFile();
//		String storageFileName = image.getOriginalFilename();
//		try {
//			String uploadDir = "src/main/resource/static/img";
//			Path uploadPath = Paths.get(uploadDir);
//			if (!Files.exists(uploadPath)) {
//				Files.createDirectories(uploadPath);
//			}
//			try (InputStream inputStream = image.getInputStream()) {
//				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
//			} catch (Exception ex) {
//				System.out.println("Exception: " + ex.getMessage());
//			}
//
//			Product product = new Product();
//			product.setName(productDto.getName());
//			product.setBrand(productDto.getBrand());
//			product.setCategory(productDto.getCategory());
//			product.setPrice(productDto.getPrice());
//			product.setDescription(productDto.getDescription());
//			product.setImageFileName(storageFileName);
//
//			service.save(product);
//			return "redirect:/products";
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			return "redirect:/products";
//		}
//	}

	@GetMapping("/create")
	public String showCreatePage(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "products/CreateProduct";
	}

	@PostMapping("/create")
	public String createProduct(@ModelAttribute Product product, BindingResult result) {
		if (product.getImageFileName() == null || product.getImageFileName().isEmpty()) {
			result.addError(new FieldError("products", "imageFileName", "The image URL is required"));
		}

		if (result.hasErrors()) {
			return "products/CreateProduct";
		}

		try {
			service.save(product);
			return "redirect:/products";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/products";
		}
	}

	@GetMapping("/LowToHigh")
	public String LowToHigh(Model model) {
		List<Product> list = service.LowToHigh();
		model.addAttribute("products", list);
		return "products/product_details";
	}

	@GetMapping("/HighToLow")
	public String HighToLow(Model model) {
		List<Product> list = service.HighToLow();
		model.addAttribute("products", list);
		return "products/product_details";
	}

	@GetMapping("/edit")
	public String showEditPage(Model model, @RequestParam int id) {
		Product product = service.findById(id).get();
		model.addAttribute("product", product);

		ProductDto productDto = new ProductDto();
		productDto.setName(product.getName());
		productDto.setBrand(product.getBrand());
		productDto.setCategory(product.getCategory());
		productDto.setPrice(product.getPrice());
		productDto.setDescription(product.getDescription());
		model.addAttribute("productDto", productDto);
		return "/products/EditProduct";
	}

	@PostMapping("/edit")
	public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto,
			BindingResult result) {
		Product product = service.findById(id).orElse(null);
		if (product == null) {
			// Handle the case where the product with the given id is not found
			return "redirect:/products";
		}

		model.addAttribute("product", product);

		if (result.hasErrors()) {
			return "products/EditProduct";
		}

		try {
			// Check if a new image is uploaded
			if (!productDto.getImageFile().isEmpty()) {
				String uploadDir = "";
				Path oldImagePath = Paths.get(uploadDir + product.getImageFileName()); // Store the old image path

				try {
					Files.delete(oldImagePath); // Delete the old image
				} catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}

				MultipartFile image = productDto.getImageFile();
				String storageFileName = image.getOriginalFilename();

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}

				// Update the image file name for the product
				product.setImageFileName(storageFileName);
			}

			// Update other fields of the product
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());

			// Save the updated product
			service.save(product);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		return "redirect:/" + "" + "products";
	}

	@DeleteMapping("/delete")
	public String deleteProduct(@PathVariable int id) {
		Optional<Product> productOptional = service.deleteData(id);
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			Path imagePath = Paths.get("src/main/resource/static/img/" + product.getImageFileName());
			try {
				Files.deleteIfExists(imagePath);
			} catch (IOException e) {
				System.out.println("Exception while deleting image file: " + e.getMessage());
			}
		} else {
			System.out.println("Product not found with id: " + id);
		}
		return "redirect:/products";
	}

	@RequestMapping("/deletebyid/{id}")
	public String deletebyid(@PathVariable Integer id) {
		repo.deleteById(id);
		return "redirect:/products";
	}

	@GetMapping("/firstFour")
	public String FirstPage(Model model) {
		System.out.println("FirstPage method called..!");
		List<Product> pList = service.getFirstPage();
		System.out.println(pList);
		model.addAttribute("products", pList);
		return "products/product_details";
	}

	@GetMapping("/secondfour")
	public String secondPage(Model model) {
		System.out.println("FirstPage method called..!");
		List<Product> pList = service.getSecondPage();
		System.out.println(pList);
		model.addAttribute("products", pList);
		return "products/product_details";
	}

	@GetMapping("/thirdfour")
	public String thirdPage(Model model) {
		System.out.println("FirstPage method called..!");
		List<Product> pList = service.getthirdPage();
		System.out.println(pList);
		model.addAttribute("products", pList);
		return "products/product_details";
	}

	@GetMapping("/search")
	public String searchProducts(@RequestParam("q") String query, Model model) {
		List<Product> products = service.searchProducts(query); // Implement this method in your service
		model.addAttribute("products", products);
		// return "products/product_details";
		return "products/landingpage";
	}
	
	
	

//	@GetMapping("/view-details")
//	public String searchProductsById(@RequestParam("id") int id, Model model) {
//
//		Product product = service.findById(id).orElse(null);
//		model.addAttribute("products", product);
//		return "products/view-product-details";
//	}
//	
//	
//	 @GetMapping("/view-details")
//	    public String viewProductDetails(@RequestParam("id") int id, Model model) {
//	        Product product = productService.getProductById(id);
//	        model.addAttribute("product", product);
//	        return "productDetails";
//	    }
	
	@GetMapping("/view-product-details")
	public String viewPage() {
		return "products/view-product-details";
	}
	
	 @GetMapping("/view-details")
	    public String viewProductDetails(@RequestParam("id") int id, Model model) {
	        Product product = service.findById(id).orElse(null);
	        if (product != null) {
	            model.addAttribute("product", product);
	            return "redirect:/products/view-product-details?id=" + id; // Redirecting to the details page
	        } else {
	            // Handle case where product with given id is not found
	            return "redirect:/"; // Redirect to home or another appropriate page
	        }       
	 }
	 



}
