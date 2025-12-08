package com.plm.poelman.java_api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.login.LoginRequest;
import com.plm.poelman.java_api.models.dto.login.LoginResponse;
import com.plm.poelman.java_api.security.JwtService;
import com.plm.poelman.java_api.security.PasswordUtils;
import com.plm.poelman.java_api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserService UserService;
    private final PasswordUtils _passwordUtils;

    public AuthController(UserService userService, JwtService jwtService, PasswordUtils passwordUtils) {
        this.UserService = userService;
        this.jwtService = jwtService;
        this._passwordUtils = passwordUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        System.out.println("Temp Hash + salt for dveerdonk@gmail.com" + _passwordUtils.generateSalt().toString() + "  " +  _passwordUtils.hash("admin".toCharArray(), _passwordUtils.generateSalt()).toString());


        if (req == null || req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("email and password are required");
        }

        System.out.println("email=" + req.getEmail());
        System.out.println("password=" + req.getPassword());

        var userOpt = UserService.Login(req);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("invalid email or password");
        }
        User user = userOpt.get();

        System.out.println("user logged in ID: " + user.getEmail());

        System.err.println("user logged in Role: " + user.getRole().getName());
        System.err.println("user logged in Email: " + user.getEmail());

        var roleName = user.getRole().getName();

        String token = jwtService.generate(user.getId().toString(),
                Map.of("email", user.getEmail(), "role", roleName));

        return ResponseEntity.ok(new LoginResponse(user, token));
    }
}
