package com.plm.poelman.java_api.controllers;

import com.plm.poelman.java_api.controllers.dto.users.CreateUserRequest;
import com.plm.poelman.java_api.controllers.dto.users.UserResponse;
import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.repositories.UserRepository;
import com.plm.poelman.java_api.security.PasswordHasher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserController(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
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
        int roleId = req.getRoleId() != 0 ? req.getRoleId() : 1;

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("name, email and password are required");
        }
        if (password.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password must be at least 8 characters");
        }

        // Uniqueness check
        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user with this email already exists");
        }

        // Hashing with salt (PBKDF2)
        byte[] salt = passwordHasher.generateSalt();
        byte[] hash = passwordHasher.hash(password.toCharArray(), salt);

        // Persist user
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordSalt(salt);
        u.setPasswordHash(hash);
        u.setRoleId(roleId);
        User saved = userRepository.save(u);

        UserResponse resp = new UserResponse(saved.getId(), saved.getEmail(), saved.getCreatedAt());
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(resp);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.getCreatedAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.getCreatedAt()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
