package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Rating() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rating(Long id, int rating, String comment, Customer customer, Restaurant restaurant) {
		super();
		this.id = id;
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", rating=" + rating + ", comment=" + comment + ", customer=" + customer
				+ ", restaurant=" + restaurant + "]";
	}

	private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

    // Getters and setters, constructors, and other fields...
}
