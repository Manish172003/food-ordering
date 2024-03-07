package com.example.demo.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Customer;
import com.example.demo.entities.OrderEntity;
import com.example.demo.entities.Restaurant;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restaurantRepository = restaurantRepository;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    public OrderEntity createOrder(OrderEntity order) {
    	System.out.println("Iam saving" + order);
        return orderRepository.save(order);
    }
    
    

    public OrderEntity updateOrder(Long orderId, OrderEntity updatedOrder) {
        OrderEntity existingOrder = getOrderById(orderId);
        // Implement update logic here
        existingOrder.setOrderDate(updatedOrder.getOrderDate());
        existingOrder.setStatus(updatedOrder.getStatus());
        // Update other fields as needed
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long orderId) {
        OrderEntity existingOrder = getOrderById(orderId);
        orderRepository.delete(existingOrder);
    }
    
    public List<OrderEntity> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }
    
    public List<OrderEntity> getOrdersByRestaurant(Restaurant restaurant) {
        return orderRepository.findByRestaurant(restaurant);
    }

   
}
