package com.service;



import org.springframework.stereotype.Service;


import com.model.UserDtls;

@Service
public interface UserService {

	UserDtls registerUser(UserDtls user);
}


