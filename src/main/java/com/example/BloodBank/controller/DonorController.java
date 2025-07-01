package com.example.BloodBank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api")
public class DonorController {

    @Autowired
    private DonorRepository donorRepository;

    @GetMapping("/donor/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable("id") Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        if (donor.isPresent()) {
            return new ResponseEntity<>(donor.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/donor")
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        try {
            Donor newDonor = donorRepository.save(donor);
            return new ResponseEntity<>(newDonor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/donor/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable("id") Long id, @RequestBody Donor donor) {
        Optional<Donor> donorData = donorRepository.findById(id);
        if (donorData.isPresent()) {
            Donor _donor = donorData.get();
            _donor.setName(donor.getName());
            _donor.setGender(donor.getGender());
            _donor.setDateOfBirth(donor.getDateOfBirth());
            _donor.setBloodGroup(donor.getBloodGroup());
            _donor.setContactNumber(donor.getContactNumber());
            _donor.setEmail(donor.getEmail());
            _donor.setAddress(donor.getAddress());
            _donor.setLastDonationDate(donor.getLastDonationDate());

            return new ResponseEntity<>(donorRepository.save(_donor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/donor/{id}")
    public ResponseEntity<HttpStatus> deleteDonor(@PathVariable("id") Long id) {
        try {
            donorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/donor")
    public ResponseEntity<HttpStatus> deleteAllDonors() {
        try {
            donorRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
