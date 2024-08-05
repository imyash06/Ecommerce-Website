package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.UserDtls;
import com.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	 private UserRepository userRepo; //i have change
	
	  @Override
	    public UserDtls registerUser(UserDtls userDtls) {
	        return userRepo.save(userDtls);
	    }
	  
	  

}

