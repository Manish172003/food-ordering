package com.example.demo.controller;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Customer;
import com.example.demo.entities.FoodItem;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.RestaurantRepository;
import com.example.demo.services.CustomerService;
import com.example.demo.services.OrderService;
import com.example.demo.services.auth.util.jwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/customer")
@CrossOrigin("http://localhost:3000/")
public class CustomerController {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final jwtUtil Jwtutil;

    @Autowired
    public CustomerController(CustomerService customerService, OrderService orderService,RestaurantRepository restaurantRepository,CustomerRepository customerRepository,jwtUtil Jwtutil) {
        this.customerService = customerService;
		this.orderService = orderService;
		this.restaurantRepository = restaurantRepository;
		this.customerRepository = customerRepository;
		this.Jwtutil = Jwtutil;
    }

//    @GetMapping
//    public List<Customer> getAllCustomers() {
//        return customerService.getAllCustomers();
//    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/createcustomer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{customerId}")
    public Customer updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {
        return customerService.updateCustomer(customerId, updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }
    
//    @PostMapping("/placeorder")
//    public ResponseEntity<OrderEntity> placeOrder(@RequestBody OrderEntity order,HttpSession session) {
//    	
//    	Long customerid = (Long)session.getAttribute("restaurantid");
//        Customer customer = customerService.getCustomerById(customerid);
//        System.out.println("This is Session "+ customerid);
//        if (customer == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//        
//        order.setCustomer(customer); 
//        OrderEntity savedOrder = orderService.createOrder(order);
//       
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
//    }
    
    @PostMapping("/placeorder")
    public ResponseEntity<OrderEntity> placeOrder(@RequestBody OrderEntity order, HttpServletRequest request) {

        String email = getUsernameFromJWT(request);
//        System.out.println("This is customer email from jwt" + email);
        Customer customer = customerRepository.findByEmail(email);
//        System.out.println("This is customer email from findbyemail" + email);
        if (customer == null) {
//        	 System.out.println("Custoemr Not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<FoodItem> foodItems = order.getFoodItems();
        

        if (foodItems == null || foodItems.isEmpty()) {
        	 System.out.println("foodItems Not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
     
        // Assuming each food item has a reference to the associated restaurant
      
     
        String resemail = foodItems.get(0).getRestaurant().getEmail();
        Restaurant restaurant = restaurantRepository.findByEmail(resemail);
//        System.out.println(restaurant.getEmail());

//
//          System.out.println("final cus :" + customer.getEmail());
            order.setCustomer(customer);
//            System.out.println("final res :" + restaurant.getName());
            order.setRestaurant(restaurant);
            
            
   

        // Create orders and return the list of saved orders
        OrderEntity savedOrders = orderService.createOrder(order);
        
        customer.getOrders().add(savedOrders);
        customerRepository.save(customer);

        // Update the restaurant's orders list
        restaurant.getOrders().add(savedOrders);
        restaurantRepository.save(restaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrders);
    }
    
    

    
    @GetMapping("/getallorders")
    public ResponseEntity<List<OrderEntity>> getCustomerOrders(HttpServletRequest request) {
    	
    	String customeremail = getUsernameFromJWT(request);
//    	Long customerid = (Long)session.getAttribute("restaurantid");
        Customer customer = customerRepository.findByEmail(customeremail);

        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<OrderEntity> customerOrders = orderService.getOrdersByCustomer(customer);

        return ResponseEntity.ok(customerOrders);
    }
    
    @GetMapping("/getactive")
    public ResponseEntity<List<Restaurant>> getactive() {
       
    	 return new ResponseEntity<>(restaurantRepository.findByIsapproved(true),HttpStatus.OK);
    }
    
    private String getUsernameFromJWT(HttpServletRequest request) {
	    String token = extractToken(request);
	  
	    return Jwtutil.extractUserName(token);
	}
   



	private String extractToken(HttpServletRequest request) {
	    // Assuming you have a method to extract the JWT token from the request header
	    String authorizationHeader = request.getHeader("Authorization");
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        return authorizationHeader.substring(7); // Remove "Bearer " to get the token
	    }
	    return null;
	}
    
    


}
