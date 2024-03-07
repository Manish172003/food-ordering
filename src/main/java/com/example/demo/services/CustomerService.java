package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Customer;
import com.example.demo.entities.OrderEntity;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.OrderRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
   
    @Autowired
    public CustomerService(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
    public OrderEntity addOrderToCustomer(Long customerId, OrderEntity order) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    public Customer createCustomer(Customer customer) {
    	Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null) {
            throw new EntityExistsException("A customer with this email already exists.");
        }

        // Implement validation logic for email, password, and other fields here, if needed.

        // Save the new customer to the database
        return customerRepository.save(customer);
//        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(customerId);
        // Implement update logic here
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        // Update other fields as needed
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = getCustomerById(customerId);
        customerRepository.delete(existingCustomer);
    }
    
    public List<OrderEntity> getCustomerOrders(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }
    
    public Customer getCustomerProfile(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
