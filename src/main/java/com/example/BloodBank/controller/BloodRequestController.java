package com.example.BloodBank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BloodBank.model.BloodRequest;
import com.example.BloodBank.repository.BloodRequestRepository;

@RestController
@RequestMapping("/api")
public class BloodRequestController {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    @GetMapping("/bloodrequest/{id}")
    public ResponseEntity<BloodRequest> getBloodRequestById(@PathVariable("id") Long id) {
        Optional<BloodRequest> bloodRequest = bloodRequestRepository.findById(id);
        if (bloodRequest.isPresent()) {
            return new ResponseEntity<>(bloodRequest.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bloodrequest")
    public ResponseEntity<BloodRequest> createBloodRequest(@RequestBody BloodRequest request) {
        try {
            BloodRequest newRequest = new BloodRequest();
            newRequest.setPatientName(request.getPatientName());
            newRequest.setBloodGroup(request.getBloodGroup());
            newRequest.setQuantity(request.getQuantity());
            newRequest.setHospitalName(request.getHospitalName());
            newRequest.setContactNumber(request.getContactNumber());
            newRequest.setStatus(request.getStatus());

            BloodRequest savedRequest = bloodRequestRepository.save(newRequest);
            return new ResponseEntity<>(savedRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bloodrequest/{id}")
    public ResponseEntity<BloodRequest> updateBloodRequest(@PathVariable("id") Long id, @RequestBody BloodRequest request) {
        Optional<BloodRequest> requestData = bloodRequestRepository.findById(id);
        if (requestData.isPresent()) {
            BloodRequest _request = requestData.get();
            _request.setPatientName(request.getPatientName());
            _request.setBloodGroup(request.getBloodGroup());
            _request.setQuantity(request.getQuantity());
            _request.setHospitalName(request.getHospitalName());
            _request.setContactNumber(request.getContactNumber());
            _request.setStatus(request.getStatus());

            return new ResponseEntity<>(bloodRequestRepository.save(_request), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/bloodrequest/{id}")
    public ResponseEntity<HttpStatus> deleteBloodRequest(@PathVariable("id") Long id) {
        try {
            bloodRequestRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bloodrequest")
    public ResponseEntity<HttpStatus> deleteAllBloodRequests() {
        try {
            bloodRequestRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
