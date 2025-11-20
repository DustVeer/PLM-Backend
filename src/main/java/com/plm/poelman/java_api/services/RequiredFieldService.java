package com.plm.poelman.java_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.dto.requiredFields.RequiredFieldResponse;
import com.plm.poelman.java_api.repositories.RequiredFieldRepository;

@Service
public class RequiredFieldService {
    
    private final RequiredFieldRepository _requiredFieldRepository;

    public RequiredFieldService(RequiredFieldRepository requiredFieldRepository) {
        this._requiredFieldRepository = requiredFieldRepository;
    }

    public List<RequiredFieldResponse> getAllRequiredFields() {
        return _requiredFieldRepository.findAll().stream()
        .map((rf) -> new RequiredFieldResponse(rf))
        .toList();
    }
    
}
