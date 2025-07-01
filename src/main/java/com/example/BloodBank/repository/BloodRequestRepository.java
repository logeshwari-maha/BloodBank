package com.example.BloodBank.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BloodBank.model.BloodRequest;

@Repository
public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    // You can define custom query methods here, e.g.:
    // List<BloodRequest> findByStatus(String status);
}
