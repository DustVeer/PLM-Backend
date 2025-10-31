package com.plm.poelman.java_api.controllers;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WhoAmIController {
    @GetMapping("/whoami")
    public Map<String, Object> whoami(Authentication auth) {
        return Map.of(
                "uri", "/api/whoami",
                "authenticated", auth != null && auth.isAuthenticated(),
                "principal", auth == null ? null : auth.getPrincipal(),
                "authorities", auth == null ? null : auth.getAuthorities());
    }
}
