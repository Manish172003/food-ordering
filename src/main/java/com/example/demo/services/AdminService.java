package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Admin;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Rating;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final OrderRepository orderRepository;
    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
  

    @Autowired
    public AdminService(AdminRepository adminRepository, RestaurantRepository restaurantRepository, OrderRepository orderRepository, RatingRepository ratingRepository) {
        this.adminRepository = adminRepository;
		this.orderRepository = orderRepository;
		this.ratingRepository = ratingRepository;
		this.restaurantRepository = restaurantRepository;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long adminId) {
        return adminRepository.findById(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found"));
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long adminId, Admin updatedAdmin) {
        Admin existingAdmin = getAdminById(adminId);
        // Implement update logic here
        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setEmail(updatedAdmin.getEmail());
        // Update other fields as needed
        return adminRepository.save(existingAdmin);
    }

    public void deleteAdmin(Long adminId) {
        Admin existingAdmin = getAdminById(adminId);
        adminRepository.delete(existingAdmin);
    }
    
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
