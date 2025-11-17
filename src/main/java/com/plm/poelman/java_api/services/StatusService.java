package com.plm.poelman.java_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.repositories.ProductStatusRepository;

@Service
public class StatusService {
    
    private final ProductStatusRepository _productStatusRepository;

    public StatusService(ProductStatusRepository productStatusRepository) {
        this._productStatusRepository = productStatusRepository;
    }

    public List<StatusResponse> getAllStatuses() {
        return _productStatusRepository.findAll().stream()
        .map((p) -> new StatusResponse(p))
        .toList();
    }

}
