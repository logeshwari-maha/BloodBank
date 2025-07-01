package com.example.BloodBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BloodBank.model.BloodInventory;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    // Optional: Custom methods like findByBloodGroup(String bloodGroup)
}
