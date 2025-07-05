package com.example.BloodBank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.server.ResponseStatusException;

import com.example.BloodBank.model.User;
import com.example.BloodBank.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Signup
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("❌ Email and password are required.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("❌ Email already registered.");
        }

        // Default role unless admin is pre-set (e.g., seeded in DB)
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ User registered successfully.");
    }

    // ✅ Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {
        if (loginData.getEmail() == null || loginData.getPassword() == null) {
            return ResponseEntity.badRequest().body("❌ Email and password required.");
        }

        Optional<User> userOpt = userRepository.findByEmailAndPassword(
            loginData.getEmail(), loginData.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> res = new HashMap<>();
            res.put("id", user.getId());
            res.put("name", user.getName());
            res.put("email", user.getEmail());
            res.put("role", user.getRole());

            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("❌ Invalid email or password.");
        }
    }

    // ✅ Get All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ Get One
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "❌ User not found"));
    }

    // ✅ Update User
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setName(updatedUser.getName());

            if (updatedUser.getRole() != null) {
                user.setRole(updatedUser.getRole());
            }

            userRepository.save(user);
            return ResponseEntity.ok("✅ User updated.");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found."));
    }

    // ✅ Delete One
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ User not found.");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("✅ User deleted.");
    }

    // ⚠️ Delete All
    @DeleteMapping("/user")
    public ResponseEntity<String> deleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok("✅ All users deleted.");
    }
}
