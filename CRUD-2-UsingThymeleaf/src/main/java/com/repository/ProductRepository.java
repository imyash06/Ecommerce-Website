package com.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import com.model.Product;



public interface ProductRepository extends JpaRepository<Product,Integer>// product model name Integer is primary key
{
	
}
