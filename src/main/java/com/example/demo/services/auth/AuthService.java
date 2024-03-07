package com.example.demo.services.auth;

import com.example.demo.dtos.SignupRequest;
import com.example.demo.dtos.UserDto;

public interface AuthService {

	UserDto createCustomer(SignupRequest signupRequest);
	UserDto createRestaurant(SignupRequest signupRequest);
	

}
