package com.example.demo.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.FoodItem;
import com.example.demo.repositories.FoodItemRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public FoodItem getFoodItemById(Long foodItemId) {
        return foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new EntityNotFoundException("Food item not found"));
    }

    public FoodItem createFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateFoodItem(Long foodItemId, FoodItem updatedFoodItem) {
        FoodItem existingFoodItem = getFoodItemById(foodItemId);
        // Implement update logic here
        existingFoodItem.setName(updatedFoodItem.getName());
        existingFoodItem.setDescription(updatedFoodItem.getDescription());
        existingFoodItem.setPrice(updatedFoodItem.getPrice());
        // Update other fields as needed
        return foodItemRepository.save(existingFoodItem);
    }
    
    public List<FoodItem> getAllFoodItemsByRestaurant(Long restaurantId) {
        return foodItemRepository.findByRestaurantId(restaurantId);
    }

    public void deleteFoodItem(Long foodItemId) {
        FoodItem existingFoodItem = getFoodItemById(foodItemId);
        foodItemRepository.delete(existingFoodItem);
    }
}
