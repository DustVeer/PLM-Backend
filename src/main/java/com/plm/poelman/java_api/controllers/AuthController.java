package com.plm.poelman.java_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.login.LoginRequest;
import com.plm.poelman.java_api.services.UserService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final UserService _UserService;

    public AuthController(UserService userService) {
        this._UserService = userService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if(req == null || req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("email and password are required");
        }
        var userOpt = _UserService.Login(req);
        if(userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("invalid email or password");
        }
        User user = userOpt.get();



        return ResponseEntity.ok(user);
    }
}
