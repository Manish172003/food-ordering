package com.example.demo.services.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.SignupRequest;
import com.example.demo.dtos.UserDto;
import com.example.demo.entities.Admin;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Restaurant;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.RestaurantRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthServiceImplementation implements AuthService {
    
	private final UserRepository userRepository;
	private final RestaurantRepository restaurantRepository;
	private final CustomerRepository customerRepository;
	private final AdminRepository adminRepository;

	public AuthServiceImplementation(UserRepository userRepository,RestaurantRepository restaurantRepository,CustomerRepository customerRepository,AdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.restaurantRepository =restaurantRepository;
		this.customerRepository = customerRepository;
		this.adminRepository =adminRepository;
	}	
	
	
	@Override
	public UserDto createCustomer(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(Role.CUSTOMER);
	    User createdUser = userRepository.save(user);
	    
	    UserDto createdUserDto = new UserDto();
	    createdUserDto.setEmail(createdUser.getEmail());
	    createdUserDto.setName(createdUser.getName());
	    createdUserDto.setRole(createdUser.getRole());
	    createdUserDto.setId(createdUser.getId());
	    
	    Customer customer = new Customer();
	    customer.setName(createdUser.getName());
	    customer.setId(createdUser.getId());
	    customer.setEmail(createdUser.getEmail());
		   System.out.println(createdUser.getId());
		   customerRepository.save(customer);
	    
	    
	 
		return createdUserDto;
	}
	
//	@PostConstruct
//	public void createAdminAccount()
//	{
//	   User adminAccount = userRepository.findByRole(Role.ADMIN);
//	   if(adminAccount == null)
//	   {
//		   User user = new User();
//		   user.setName("admin");
//		   user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//		   user.setEmail("admin@gmail.com");
//		   user.setRole(Role.ADMIN);
//		   userRepository.save(user);
//		  
//		   
//	   }
//	}
	public UserDto createRestaurant(SignupRequest signupRequest) {
		User user = new User();
		user.setName(signupRequest.getName());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
		user.setRole(Role.RESTAURANT);
	
	    User createdUser = userRepository.save(user);
	    
	    UserDto createdUserDto = new UserDto();
	    createdUserDto.setEmail(createdUser.getEmail());
	    createdUserDto.setName(createdUser.getName());
	    createdUserDto.setRole(createdUser.getRole());
	    createdUserDto.setId(createdUser.getId());
	    
	    Restaurant restaurant = new Restaurant();
	       restaurant.setName(createdUser.getName());
		   restaurant.setEmail(createdUser.getEmail()); // null
		   restaurant.setId(createdUser.getId());
		   restaurant.setIsapproved(true);
		   restaurant.setActive(true);
//		   restaurant.se
		   restaurantRepository.save(restaurant);
	    
	    
	 
		return createdUserDto;
	}


	

}
