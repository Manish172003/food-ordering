package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(Long id, String name, String email, String password, AdminRole role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private AdminRole role;
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
	public AdminRole getRole() {
		return role;
	}
	public void setRole(AdminRole role) {
		this.role = role;
	}
//	@Override
//	public String toString() {
//		return "Admin [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
//				+ "]";
//	}

    // Getters and setters, constructors, and other fields...
}