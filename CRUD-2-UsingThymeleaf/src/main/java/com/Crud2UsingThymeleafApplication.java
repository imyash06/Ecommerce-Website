package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Crud2UsingThymeleafApplication {

	public static void main(String[] args) {
		System.out.println("application start");
		SpringApplication.run(Crud2UsingThymeleafApplication.class, args);
		System.out.println("application stop");
	}

}
