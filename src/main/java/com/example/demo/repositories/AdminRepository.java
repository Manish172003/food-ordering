package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // You can define custom query methods here if needed
}
