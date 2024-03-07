package com.example.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.FoodItemService;
import com.example.demo.services.OrderService;
import com.example.demo.services.RestaurantService;
import com.example.demo.services.auth.util.jwtUtil;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.dtos.AuthenticationResponse;
import com.example.demo.entities.Customer;
import com.example.demo.entities.FoodItem;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.RestaurantRepository;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin("http://localhost:3000/")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    private final OrderService orderService;
    private final RestaurantRepository restaurantRepository;
    private final jwtUtil Jwtutil;
    
    
    private final AuthController authController;
//	private jwtUtil jwtutil;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, FoodItemService foodItemService,AuthController authController,RestaurantRepository restaurantRepository,jwtUtil Jwtutil,OrderService orderService) {
        this.restaurantService = restaurantService;
		this.foodItemService = foodItemService;
		this.authController = authController;
		this.restaurantRepository = restaurantRepository;
		this.Jwtutil = Jwtutil;
		this.orderService = orderService;
    }

    @GetMapping("/allrestaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
    
    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurant(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PostMapping("/createrestaurant")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.createRestaurant(restaurant);
    }

    @PutMapping("/{restaurantId}")
    public Restaurant updateRestaurant(@PathVariable Long restaurantId, @RequestBody Restaurant updatedRestaurant) {
        return restaurantService.updateRestaurant(restaurantId, updatedRestaurant);
    }

//    @DeleteMapping("/{restaurantId}")
//    public void deleteRestaurant(@PathVariable Long restaurantId) {
//        restaurantService.deleteRestaurant(restaurantId);
//    }
    
    @PostMapping("/addfood")
    public ResponseEntity<FoodItem> addFoodItemToRestaurant(@RequestBody FoodItem foodItem, HttpServletRequest request) {
        String email = getUsernameFromJWT(request);
        System.out.println("Decoded Email: " + email);

        // Retrieve the restaurant by email
        Restaurant res = restaurantRepository.findByEmail(email);

        if (res == null) {
            System.out.println("Restaurant not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Associate the food item with the restaurant
        foodItem.setRestaurant(res);

        // Save the food item
        FoodItem savedFoodItem = foodItemService.createFoodItem(foodItem);

        // Return the saved food item with a CREATED status
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFoodItem);
    }

	    
//    @GetMapping("/getallfooditems")
//    public List<FoodItem> getAllFoodItemsOfRestaurant(HttpSession session) {
//    	Long restaurantid = (Long)session.getAttribute("restaurantid");
//        return foodItemService.getAllFoodItemsByRestaurant(restaurantid);
//    }
    
    @DeleteMapping("/{foodItemId}")
    public ResponseEntity<FoodItem> deleteFoodItem(@PathVariable Long foodItemId, HttpServletRequest request) {

        String email = getUsernameFromJWT(request);
        System.out.println("Decoded Email: " + email);

        // Retrieve the restaurant by email
        Restaurant res = restaurantRepository.findByEmail(email);

        if (res == null) {
            System.out.println("Restaurant not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Check if the food item belongs to the restaurant
        FoodItem foodItemToDelete = foodItemService.getFoodItemById(foodItemId);
        if (foodItemToDelete == null || !foodItemToDelete.getRestaurant().equals(res)) {
            System.out.println("Food item not found or does not belong to the restaurant");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        foodItemService.deleteFoodItem(foodItemId);
        return ResponseEntity.status(HttpStatus.OK).body(foodItemToDelete);
    }

	 
	 @GetMapping("/getallfooditems")
      public ResponseEntity<List<FoodItem>> getAllFoodItemsOfRestaurant(HttpServletRequest request) {
		 String username = getUsernameFromJWT(request);

		    // Assuming you have a method in your user service to find by email
		 Restaurant res = restaurantRepository.findByEmail(username);

		 // Assuming you have a method in your restaurant repository to find by email
		 Restaurant restaurant = restaurantRepository.findByEmail(username);

		 // Check if the restaurant exists
		 if (restaurant != null) {
		     Long restaurantId = restaurant.getId();
		     List<FoodItem> foodItems = foodItemService.getAllFoodItemsByRestaurant(restaurantId);
		     return ResponseEntity.ok(foodItems);
		 } else {
		     return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
		 }

	 }
	 
	    @GetMapping("/getallorders")
	    public ResponseEntity<List<OrderEntity>> getCustomerOrders(HttpServletRequest request) {
	    	
	    	String restaurantemail = getUsernameFromJWT(request);
//	    	Long customerid = (Long)session.getAttribute("restaurantid");
	        Restaurant restaurant = restaurantRepository.findByEmail(restaurantemail);

	        if (restaurant == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        List<OrderEntity> customerOrders = orderService.getOrdersByRestaurant(restaurant);

	        return ResponseEntity.ok(customerOrders);
	    }
	    
	 
	   private String getUsernameFromJWT(HttpServletRequest request) {
		    String token = extractToken(request);
		    // Assuming you have a method to extract the username from the JWT token
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
		
		@GetMapping("/getactive")
	    public ResponseEntity<List<Restaurant>> getactive() {
	       
	    	 return new ResponseEntity<>(restaurantRepository.findByActive(true),HttpStatus.OK);
	    }
		
	 
   	
	 

}
