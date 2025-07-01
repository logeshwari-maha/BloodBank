package com.example.BloodBank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BloodBank.model.BloodInventory;
import com.example.BloodBank.repository.BloodInventoryRepository;

@RestController
@RequestMapping("/api")
public class BloodInventoryController {

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @GetMapping("/bloodinventory/{id}")
    public ResponseEntity<BloodInventory> getInventoryById(@PathVariable("id") Long id) {
        Optional<BloodInventory> inventory = bloodInventoryRepository.findById(id);
        if (inventory.isPresent()) {
            return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bloodinventory")
    public ResponseEntity<BloodInventory> createInventory(@RequestBody BloodInventory inventory) {
        try {
            BloodInventory newInventory = new BloodInventory();
            newInventory.setBloodGroup(inventory.getBloodGroup());
            newInventory.setQuantity(inventory.getQuantity());

            BloodInventory savedInventory = bloodInventoryRepository.save(newInventory);
            return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bloodinventory/{id}")
    public ResponseEntity<BloodInventory> updateInventory(@PathVariable("id") Long id, @RequestBody BloodInventory inventory) {
        Optional<BloodInventory> inventoryData = bloodInventoryRepository.findById(id);
        if (inventoryData.isPresent()) {
            BloodInventory _inventory = inventoryData.get();
            _inventory.setBloodGroup(inventory.getBloodGroup());
            _inventory.setQuantity(inventory.getQuantity());

            return new ResponseEntity<>(bloodInventoryRepository.save(_inventory), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/bloodinventory/{id}")
    public ResponseEntity<HttpStatus> deleteInventory(@PathVariable("id") Long id) {
        try {
            bloodInventoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/bloodinventory")
    public ResponseEntity<HttpStatus> deleteAllInventories() {
        try {
            bloodInventoryRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
