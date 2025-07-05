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

import com.example.BloodBank.model.BloodInventory;
import com.example.BloodBank.repository.BloodInventoryRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    // GET all inventory items
    @GetMapping("/bloodinventory")
    public ResponseEntity<List<BloodInventory>> getAllInventory() {
        try {
            List<BloodInventory> list = bloodInventoryRepository.findAll();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET inventory item by ID
    @GetMapping("/bloodinventory/{id}")
    public ResponseEntity<BloodInventory> getInventoryById(@PathVariable Long id) {
        Optional<BloodInventory> inventory = bloodInventoryRepository.findById(id);
        return inventory
                .map(data -> new ResponseEntity<>(data, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // POST - create new inventory
    @PostMapping("/bloodinventory")
    public ResponseEntity<BloodInventory> createInventory(@RequestBody BloodInventory inventory) {
        try {
            BloodInventory saved = bloodInventoryRepository.save(inventory);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - update inventory by ID
    @PutMapping("/bloodinventory/{id}")
    public ResponseEntity<BloodInventory> updateInventory(@PathVariable Long id, @RequestBody BloodInventory inventory) {
        Optional<BloodInventory> data = bloodInventoryRepository.findById(id);
        if (data.isPresent()) {
            BloodInventory existing = data.get();
            existing.setBloodGroup(inventory.getBloodGroup());
            existing.setQuantity(inventory.getQuantity());
            return new ResponseEntity<>(bloodInventoryRepository.save(existing), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE by ID
    @DeleteMapping("/bloodinventory/{id}")
    public ResponseEntity<HttpStatus> deleteInventory(@PathVariable Long id) {
        try {
            bloodInventoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE all inventory
    @DeleteMapping("/bloodinventory")
    public ResponseEntity<HttpStatus> deleteAllInventory() {
        try {
            bloodInventoryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
