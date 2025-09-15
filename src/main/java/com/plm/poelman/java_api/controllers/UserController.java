package com.plm.poelman.java_api.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.controllers.dto.users.CreateUserRequest;
import com.plm.poelman.java_api.controllers.dto.users.UserResponse;
import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.repositories.UserRepository;
import com.plm.poelman.java_api.security.PasswordUtils;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository _UserRepository;
    private final PasswordUtils _PasswordUtils;

    public UserController(UserRepository userRepository, PasswordUtils passwordUtils) {
        this._UserRepository = userRepository;
        this._PasswordUtils = passwordUtils;
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
        Optional<User> existing = _UserRepository.findByEmail(email);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user with this email already exists");
        }

        // Hashing with salt (PBKDF2)
        byte[] salt = _PasswordUtils.generateSalt();
        byte[] hash = _PasswordUtils.hash(password.toCharArray(), salt);

        // Persist user
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPasswordSalt(salt);
        u.setPasswordHash(hash);
        u.setRoleId(roleId);
        User saved = _UserRepository.save(u);

        UserResponse resp = new UserResponse(saved.getId(), saved.getEmail(), saved.getCreatedAt());
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(resp);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return _UserRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.getCreatedAt()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return _UserRepository.findById(id)
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.getCreatedAt()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        return _UserRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(
                new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getCreatedAt()
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
