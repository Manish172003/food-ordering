package com.example.demo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Customer;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Restaurant;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // You can define custom query methods here if needed
	   List<OrderEntity> findAllByCustomerId(Long customerId);
	    List<OrderEntity> findAllByRestaurantId(Long restaurantId);
	    List<OrderEntity> findByCustomer(Customer customer);
	    List<OrderEntity> findByRestaurant(Restaurant restaurant);

}
