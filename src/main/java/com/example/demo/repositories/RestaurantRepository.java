package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.repository.CrudRepository;


import com.example.demo.entities.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	 List<Restaurant> findByIsapproved(boolean isapproved);
	 List<Restaurant> findByActive(boolean active);
	 Restaurant findByEmail(String username);


}

