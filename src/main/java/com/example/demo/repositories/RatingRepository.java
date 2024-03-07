package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // You can define custom query methods here if needed
}
