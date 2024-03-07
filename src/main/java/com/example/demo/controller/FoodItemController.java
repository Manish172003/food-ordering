package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.FoodItem;
import com.example.demo.services.FoodItemService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fooditems")
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/{foodItemId}")
    public FoodItem getFoodItem(@PathVariable Long foodItemId) {
        return foodItemService.getFoodItemById(foodItemId);
    }

    @PostMapping
    public FoodItem createFoodItem(@RequestBody FoodItem foodItem) {
        return foodItemService.createFoodItem(foodItem);
    }

    @PutMapping("/{foodItemId}")
    public FoodItem updateFoodItem(@PathVariable Long foodItemId, @RequestBody FoodItem updatedFoodItem) {
        return foodItemService.updateFoodItem(foodItemId, updatedFoodItem);
    }

    @DeleteMapping("/{foodItemId}")
    public void deleteFoodItem(@PathVariable Long foodItemId, HttpServletRequest re) {
        foodItemService.deleteFoodItem(foodItemId);
    }
}
