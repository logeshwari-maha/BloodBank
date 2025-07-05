package com.example.BloodBank.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BloodBank.model.Donor;
import com.example.BloodBank.repository.DonorRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    // ✅ Get all donors
    @GetMapping("/donors")
    public ResponseEntity<List<Donor>> getAllDonors() {
        try {
            List<Donor> donors = donorRepository.findAll();
            return new ResponseEntity<>(donors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Get donor by ID
    @GetMapping("/donors/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable("id") Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        return donor.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ✅ Add new donor
    @PostMapping("/donors")
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        try {
            Donor savedDonor = donorRepository.save(donor);
            return new ResponseEntity<>(savedDonor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Update donor by ID
    @PutMapping("/donors/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable("id") Long id, @RequestBody Donor donor) {
        Optional<Donor> donorData = donorRepository.findById(id);

        if (donorData.isPresent()) {
            Donor existingDonor = donorData.get();
            existingDonor.setName(donor.getName());
            existingDonor.setGender(donor.getGender());
            existingDonor.setDateOfBirth(donor.getDateOfBirth());
            existingDonor.setBloodGroup(donor.getBloodGroup());
            existingDonor.setContactNumber(donor.getContactNumber());
            existingDonor.setEmail(donor.getEmail());
            existingDonor.setAddress(donor.getAddress());
            existingDonor.setLastDonationDate(donor.getLastDonationDate());

            return new ResponseEntity<>(donorRepository.save(existingDonor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Delete donor by ID
    @DeleteMapping("/donors/{id}")
    public ResponseEntity<HttpStatus> deleteDonor(@PathVariable("id") Long id) {
        try {
            donorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Delete all donors
    @DeleteMapping("/donors")
    public ResponseEntity<HttpStatus> deleteAllDonors() {
        try {
            donorRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
