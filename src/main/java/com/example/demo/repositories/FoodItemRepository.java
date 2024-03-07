package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    // You can define custom query methods here if needed
	 List<FoodItem> findAllByRestaurantId(Long restaurantId);
	 List<FoodItem> findByRestaurantId(Long restaurantId);
}

