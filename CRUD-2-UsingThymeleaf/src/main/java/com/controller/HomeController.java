package com.controller;


import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.model.UserDtls;
import com.repository.UserRepository;
import com.service.UserService;



@Controller

public class HomeController {

	@Autowired
	UserRepository repo;

	@Autowired
	UserService userService;

	@PostMapping("/createUser")
	public String registerUser(@ModelAttribute UserDtls userDtls, RedirectAttributes redirectAttributes) {
		System.out.println("User Details:" + userDtls);
		userService.registerUser(userDtls);
		redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
		return "redirect:/login";
	}
	
//correct code 
	@PostMapping("/login") // we have use request param so we need to use form data in postman.
	public ResponseEntity<UserDtls> login(@RequestParam String email, @RequestParam String password) {
		Optional<UserDtls> user = repo.findByEmailAndPassword(email, password);

	if (user.isPresent()) {
		return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

	}
	
//	 @PostMapping("/login")
//	    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
//	        Optional<UserDtls> user = repo.findByEmailAndPassword(email, password);
//	        
//	       // System.out.println("  user  "+user);
//	        System.out.println("  user  "+user.isPresent());
//
//	        if (user.isPresent()) {
//	            session.setAttribute("user", user.get());
//	        	return "Dashboard";// Redirect to the landing page
//	        } else {
//	            model.addAttribute("error", "Invalid email or password");
//	            return "products/login"; // Return to the login page with an error message
//	        }
//	    }
	



	@GetMapping("/Dashboard")
	public String Dashboard() {
		return "products/Dashboard";
	}
	


	@GetMapping("/register")
	public String register() {
		return "products/register";
	}

	@GetMapping("/")
	public String landingpage() {
		return "products/landingpage";
	}

	@GetMapping("/login")
	public String login() {
		return "products/login";
	}

	@GetMapping("/logout")
	public String logout(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("logoutSuccess", "You have been logged out successfully!");
		return "redirect:/";
	}
	
}
