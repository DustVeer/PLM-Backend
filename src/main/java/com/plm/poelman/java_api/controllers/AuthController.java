package com.plm.poelman.java_api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.login.LoginRequest;
import com.plm.poelman.java_api.models.dto.login.LoginResponse;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.security.JwtService;
import com.plm.poelman.java_api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService UserService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.UserService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req == null || req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("email and password are required");
        }

        var userOpt = UserService.Login(req);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("invalid email or password");
        }
        User user = userOpt.get();

        System.err.println("user logged in Role: " + user.getRole().getName());
        System.err.println("user logged in Email: " + user.getEmail());


        UserResponse userResponse = new UserResponse(user);


        var roleName = user.getRole().getName();

        String token = jwtService.generate(user.getId().toString(),
                Map.of("email", user.getEmail(), "role", roleName));

        return ResponseEntity.ok(new LoginResponse(user, token));
    }
}
