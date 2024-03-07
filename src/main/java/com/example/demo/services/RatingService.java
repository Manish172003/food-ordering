package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import com.example.demo.entities.Rating;
import com.example.demo.repositories.RatingRepository;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Long ratingId, Rating updatedRating) {
        Rating existingRating = getRatingById(ratingId);
        // Implement update logic here
        existingRating.setRating(updatedRating.getRating());
        existingRating.setComment(updatedRating.getComment());
        // Update other fields as needed
        return ratingRepository.save(existingRating);
    }

    public void deleteRating(Long ratingId) {
        Rating existingRating = getRatingById(ratingId);
        ratingRepository.delete(existingRating);
    }
}
