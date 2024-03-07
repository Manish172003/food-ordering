
package com.example.demo.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;



@Entity
//@JsonIgnoreProperties("foodItems")
public class Restaurant {

    @Id
    private Long id;
	private String name;
	@Column(name = "email")
    private String email;
    private String password;
    private String phone;
    private String address;
    private boolean active;
    private boolean isapproved;

    public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

//	@Override
//	public String toString() {
//		return "Restaurant [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone="
//				+ phone + ", address=" + address + ", active=" + active + ", isapproved=" + isapproved + ", foodItems="
//				+ foodItems + ", orders=" + orders + "]";
//	}
//	
	
	
	@OneToMany(mappedBy = "restaurant")
	@JsonIgnoreProperties("restaurant")
	private List<FoodItem> foodItems;
   
	@OneToMany(mappedBy = "restaurant" , cascade = CascadeType.ALL)
	@JsonIgnoreProperties("restaurant")
	private List<OrderEntity> orders;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isIsapproved() {
		return isapproved;
	}

	public void setIsapproved(boolean isapproved) {
		this.isapproved = isapproved;
	}

	public List<FoodItem> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<FoodItem> foodItems) {
		this.foodItems = foodItems;
	}

	public List<OrderEntity> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}

	public Restaurant(Long id, String name, String email, String password, String phone, String address, boolean active,
			boolean isapproved, List<FoodItem> foodItems, List<OrderEntity> orders) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.active = active;
		this.isapproved = isapproved;
		this.foodItems = foodItems;
		this.orders = orders;
	}


}