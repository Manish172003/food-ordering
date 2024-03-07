package com.example.demo.controller;

import java.io.IOException;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.dtos.AuthenticationRequest;
import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.dtos.SignupRequest;
import com.example.demo.dtos.UserDto;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.auth.AuthService;
import com.example.demo.services.auth.jwt.UserService;
import com.example.demo.services.auth.util.jwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;	

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000/")
public class AuthController {
	
	private final AuthService authService; 
	
	private final AuthenticationManager authenticationManager;
	
	private final UserService userDetailsService;
	
	private final jwtUtil Jwtutil;
	
	private final UserRepository userRepository;
	

	
	public AuthController(AuthService authService, AuthenticationManager authenticationManager,
			UserService userDetailsService, jwtUtil Jwtutil, UserRepository userRepository) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.Jwtutil = Jwtutil;
		this.userRepository = userRepository;
	}


	
	@PostMapping("/customer/signup")
	public ResponseEntity<?> customersignup(@RequestBody  SignupRequest signupRequest)
	{
		UserDto createdUserDto  = authService.createCustomer(signupRequest);
		if(createdUserDto == null)
		{
			return new ResponseEntity<>("User Not Created", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	@PostMapping("/restaurant/signup")
	public ResponseEntity<?> restaurantsignup(@RequestBody  SignupRequest signupRequest)
	{
		UserDto createdUserDto  = authService.createRestaurant(signupRequest);
		if(createdUserDto == null)
		{
			return new ResponseEntity<>("Restaurant Not Created", HttpStatus.BAD_REQUEST);
		}
		
		
		
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	

	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response, HttpSession session) throws IOException
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect UserName or Password");
		}catch(DisabledException disabledException)
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"User is Not Active");
			return null;
		}
		
		final UserDetails userDetails = userDetailsService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = Jwtutil.generateToken(userDetails);
		Optional<com.example.demo.entities.User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
	    
	  
		
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		
		if(optionalUser.isPresent())
		{
			session.setAttribute("restaurantid", optionalUser.get().getId());
			authenticationResponse.setJwt(jwt);
			authenticationResponse.setRole(optionalUser.get().getRole());
			authenticationResponse.setUserid(optionalUser.get().getId());
			authenticationResponse.setUserName(optionalUser.get().getName());
		}
		return  authenticationResponse;
		
	}
	
	
	

}
