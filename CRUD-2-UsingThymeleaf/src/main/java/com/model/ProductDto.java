package com.model;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto 
{
	@Id
	private int id;
   public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

@NotEmpty(message = "the name is required")
private String name;

@NotEmpty(message = "the brand is required")
private String brand;

@NotEmpty(message = "the category is required")
private String category;

@Min(0)
private double price;

@Size(min = 10,message = "the description should be at least 10 character")
@Size(max = 2000,message = "the description cannot exceed 2000 character")
private String description;

private MultipartFile imageFile; 
//In Java, MultipartFile is a class typically used in web applications, especially in the context of file uploading. 


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getBrand() {
	return brand;
}

public void setBrand(String brand) {
	this.brand = brand;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public double getPrice() {
	return price;
}

public void setPrice(@Min(0) double price) {
	this.price = price;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public MultipartFile getImageFile() {
	return imageFile;
}

public void setImageFile(MultipartFile imageFile) {
	this.imageFile = imageFile;
}

}
