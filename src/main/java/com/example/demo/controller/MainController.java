//package com.example.demo.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
////import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.dao.CustomerRepository;
////import com.example.demo.dao.CustomerRepositoryImpl;
//import com.example.demo.entities.Customer;
////import com.example.demo.entities.User;
//
//
//@Controller
//public class MainController {
//
//	@Autowired
//	private CustomerRepository repo;
//	
//	@RequestMapping("/home")
//	public String home()
//	{
//		return "home";
//	}
//	
//	@RequestMapping("/contact")
//	public String contact()
//	{
//		return "contact";
//	}
//	
//	@RequestMapping("/register")
//	public String register()
//	{
//		return "register";
//	}
//	
//	@RequestMapping("/processregister")
//	public String processregister(@ModelAttribute Customer cus)
//	{
//		Customer c1 = new Customer(cus.getEmail(),cus.getPassword());
////		CustomerRepositoryImpl repo = new  CustomerRepositoryImpl();
////		repo.save(c1);
//		repo.save(c1);
//		return "regsuccess";
//	}
//	
//	@RequestMapping("/signin")
//	public String signin()
//	{
//		return "signin";
//	}
//	
//	@RequestMapping("/processsignin")
//	public String processsignin(@ModelAttribute Customer cus)
//	{
//		
//	   List<Customer> list = repo.findDistinctByEmail(cus.getEmail());
//         
//	   if( list != null && (list.get(0).getPassword().equals(cus.getPassword()) ))
//	   {
//		   return "signinsuccess";
//	   }
//	   
//		return "failure";
//	}
//	
//	
//}
