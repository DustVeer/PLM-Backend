package com.plm.poelman.java_api.controllers;

import com.plm.poelman.java_api.repositories.DbTestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestController {

    private final DbTestRepository repo;

    public DbTestController(DbTestRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/api/dbtest")
    public String testDb() {
        return repo.testConnection();
    }
}
