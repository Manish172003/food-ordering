package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.RestaurantRepository;
import com.example.demo.services.AdminService;
import com.example.demo.services.CustomerService;
import com.example.demo.services.RestaurantService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;
    private final CustomerService customerService; 
    
    @GetMapping("/login")
    public String Login() {
        return "login";
    } 

    @Autowired
    public AdminController(AdminService adminService,RestaurantRepository restaurantRepository,RestaurantService restaurantService,CustomerService customerService) {
        this.adminService = adminService;
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
        this.customerService = customerService;
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{adminId}")
    public Admin getAdmin(@PathVariable Long adminId) {
        return adminService.getAdminById(adminId);
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/{adminId}")
    public Admin updateAdmin(@PathVariable Long adminId, @RequestBody Admin updatedAdmin) {
        return adminService.updateAdmin(adminId, updatedAdmin);
    }

    @DeleteMapping("/{adminId}")
    public void deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
    }
    
    @PostMapping("/approve/{restaurantId}")
    public ResponseEntity<String> approveRestaurant(@PathVariable Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant  restaurant = optionalRestaurant.get();
            restaurant.setIsapproved(true);
            restaurant.setActive(true);
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok("Restaurant approved.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/pending")
    public ResponseEntity<List<Restaurant>> pending() {
       
    	 return new ResponseEntity<>(restaurantRepository.findByIsapproved(false),HttpStatus.OK);
    }
    
    
    @PostMapping("/makeactive/{restaurantId}")
    public ResponseEntity<String> makeactive(@PathVariable Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setActive(true);
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok("Restaurant made Active.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/makeinactive/{restaurantId}")
    public ResponseEntity<String> makeinactive(@PathVariable Long restaurantId) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setActive(false);
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok("Restaurant made Inactive");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getrestaurants")
    public ResponseEntity<List<Restaurant>> getallrestaurants() {
    	 List<Restaurant> restaurants = restaurantService.getAllRestaurants();
         return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
    
    @GetMapping("/getcustomers")
    public ResponseEntity<List<Customer>> getallcustomers() {
    	 List<Customer> customers = customerService.getAllCustomers();
         return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
  
}
