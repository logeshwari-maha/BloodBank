package com.example.BloodBank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BloodBank.model.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long> {}
