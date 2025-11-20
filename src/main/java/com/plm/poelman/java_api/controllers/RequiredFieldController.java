package com.plm.poelman.java_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.services.RequiredFieldService;

import com.plm.poelman.java_api.models.dto.requiredFields.RequiredFieldResponse;

@RestController
@RequestMapping("/api/requiredfields")
public class RequiredFieldController {
    
    private final RequiredFieldService _requiredFieldService;

    public RequiredFieldController(RequiredFieldService requiredFieldService) {
        this._requiredFieldService = requiredFieldService;
    }

    @GetMapping
    public List<RequiredFieldResponse> getAllRequiredFields() {
        return _requiredFieldService.getAllRequiredFields();
    }    
}
