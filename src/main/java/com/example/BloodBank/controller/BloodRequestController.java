package com.example.BloodBank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloodBank.model.BloodRequest;
import com.example.BloodBank.repository.BloodRequestRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/requests")
public class BloodRequestController {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    // POST: Create new request with default status as "PENDING"
    @PostMapping
    public ResponseEntity<BloodRequest> createRequest(@RequestBody BloodRequest request) {
        request.setStatus("PENDING");
        BloodRequest savedRequest = bloodRequestRepository.save(request);
        return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
    }

    // GET: Get all requests
    @GetMapping
    public ResponseEntity<List<BloodRequest>> getAllRequests() {
        List<BloodRequest> requests = bloodRequestRepository.findAll();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    // PUT: Approve request by ID
    @PutMapping("/{id}/approve")
    public ResponseEntity<BloodRequest> approveRequest(@PathVariable Long id) {
        Optional<BloodRequest> optional = bloodRequestRepository.findById(id);
        if (optional.isPresent()) {
            BloodRequest request = optional.get();
            request.setStatus("APPROVED");
            return new ResponseEntity<>(bloodRequestRepository.save(request), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // PUT: Reject request by ID
    @PutMapping("/{id}/reject")
    public ResponseEntity<BloodRequest> rejectRequest(@PathVariable Long id) {
        Optional<BloodRequest> optional = bloodRequestRepository.findById(id);
        if (optional.isPresent()) {
            BloodRequest request = optional.get();
            request.setStatus("REJECTED");
            return new ResponseEntity<>(bloodRequestRepository.save(request), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
