package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.model.Product;
import com.repository.ProductRepository;


@Service
public class ProductService {
	@Autowired 
	ProductRepository repo;

	public List<Product> findAll() {
		return repo.findAll();
	}

	public void save(Product product) {
		repo.save(product);
	}

	public List<Product> LowToHigh()
	{
		
		PageRequest request = PageRequest.of(0, 10, Sort.by("price").ascending());
	    Page<Product> page = repo.findAll(request);
	    return page.getContent();
	}

	public List<Product> HighToLow()
	{
		PageRequest request = PageRequest.of(0, 10, Sort.by("price").descending());
	    Page<Product> page = repo.findAll(request);
	    return page.getContent();
	}

	public Optional<Product> findById(int id) {	
		return repo.findById(id);
	}

//	public List<Product> deleteData(int id) {
//		repo.deleteById(id);
//		return repo.findAll();
	
	 public Optional<Product> deleteData(int id) {
	        Optional<Product> product = repo.findById(id);
	        if (product.isPresent()) {
	            repo.deleteById(id);
	        }
	        return product;
	    }
		
	

	public List<Product> getFirstPage()
	{
		PageRequest request = PageRequest.of(0, 3);
		Page<Product> page = repo.findAll(request);
		return page.getContent();
	}

	public List<Product> getSecondPage() 
	{
		PageRequest request = PageRequest.of(1, 3);
		Page<Product> page = repo.findAll(request);
		return page.getContent();
	}

	public List<Product> getthirdPage() {
		PageRequest request = PageRequest.of(2, 3);
		Page<Product> page = repo.findAll(request);
		return page.getContent();
	}

	public List<Product> searchProducts(String query) { //for search
        List<Product> allProducts = repo.findAll();
        return allProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(query.toLowerCase()) ||
                                   product.getBrand().toLowerCase().contains(query.toLowerCase()) ||
                                   product.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
	 
	
	

}
