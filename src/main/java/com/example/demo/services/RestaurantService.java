package com.example.demo.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import com.example.demo.entities.FoodItem;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.FoodItemRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, OrderRepository orderRepository, FoodItemRepository foodItemRepository) {
        this.restaurantRepository = restaurantRepository;
		this.foodItemRepository = foodItemRepository;
		this.orderRepository = orderRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long restaurantId, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = getRestaurantById(restaurantId);
        // Implement update logic here
        existingRestaurant.setName(updatedRestaurant.getName());
        existingRestaurant.setEmail(updatedRestaurant.getEmail());
        // Update other fields as needed
        return restaurantRepository.save(existingRestaurant);
    }

    public void deleteRestaurant(Long restaurantId) {
        Restaurant existingRestaurant = getRestaurantById(restaurantId);
        restaurantRepository.delete(existingRestaurant);
    }
    public List<FoodItem> getRestaurantFoodItems(Long restaurantId) {
        return foodItemRepository.findAllByRestaurantId(restaurantId);
    }

    public List<OrderEntity> getRestaurantOrders(Long restaurantId) {
        return orderRepository.findAllByRestaurantId(restaurantId);
    }

    public Restaurant setRestaurantStatus(Long restaurantId, boolean active) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));
        restaurant.setActive(active);
        return restaurantRepository.save(restaurant);
    }
}
