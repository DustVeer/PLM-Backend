package com.plm.poelman.java_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String helloWorld() {
        return "Hello World from Java API 🚀";
    }
}