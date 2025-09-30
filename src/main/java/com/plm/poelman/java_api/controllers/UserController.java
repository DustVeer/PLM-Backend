package com.plm.poelman.java_api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.users.CreateUserRequest;
import com.plm.poelman.java_api.models.dto.users.UserResponse;

import com.plm.poelman.java_api.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService _userService;
    

    public UserController(UserService userService) {
        this._userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest req) {
        // Basic validation
        if (req == null || req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email and password are required");
        }
        String name = req.getName() != null ? req.getName().trim() : "";
        String email = req.getEmail().trim().toLowerCase();
        String password = req.getPassword();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("name, email and password are required");
        }
        if (password.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password must be at least 8 characters");
        }

       
        Optional<User> existing = _userService.findByEmail(email);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user with this email already exists");
        }

        User saved = _userService.save(req);

        UserResponse resp = new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getCreatedAt());
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(resp);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return _userService.getAllUsersResponse();
                
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        return _userService.findById(id).stream().map(u -> new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getCreatedAt()))
                .map(ResponseEntity::ok)
                .findFirst()
                .orElseGet(() -> ResponseEntity.notFound().build());
                
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        return _userService.findByEmail(email)
                .map(u -> new UserResponse(
                u.getId(),
                u.getName(),
                u.getEmail(),
                u.getCreatedAt()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
