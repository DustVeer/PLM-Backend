package com.plm.poelman.java_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.dto.statuses.CreateUpdateStatusRequest;
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

    public StatusResponse getStatusById(Long id) {
        if (id == null) {
            return null;
        }

        return _productStatusRepository.findById(id)
        .map((p) -> new StatusResponse(p))
        .orElse(null);
    }

    public ProductStatus getByName(String name) {
        if (name == null) {
            return null;
        }
        Optional<ProductStatus> status = _productStatusRepository.findByName(name);
        if (status.isEmpty()) {
            return null;
        }
        return status.get();
    }

    public ProductStatus getBySortOrder(Long sortOrder) {
        if (sortOrder == null) {
            return null;
        }
        Optional<ProductStatus> status = _productStatusRepository.findBySortOrder(sortOrder);
        System.out.println("Status:" + status);
        if (status.isEmpty()) {
            return null;
        }
        return status.get();
    }

    public List<StatusResponse> getAllActiveStatuses() {
        return _productStatusRepository.findByActive(1).stream()
        .map((p) -> new StatusResponse(p))
        .toList();
    }

    public StatusResponse createStatus(ProductStatus newStatus) {
        if (newStatus == null) {
            return null;
        }
        return new StatusResponse(_productStatusRepository.save(newStatus));
    }

    public StatusResponse updateStatus(Long id, CreateUpdateStatusRequest req) {
        if (id == null || req == null) {
            return null;
        }

        Optional<ProductStatus> existingStatusOpt = _productStatusRepository.findById(id);
        if (existingStatusOpt.isEmpty()) {
            return null;
        }

        ProductStatus existingStatus = existingStatusOpt.get();
        existingStatus.setName(req.getName());
        existingStatus.setDescription(req.getDescription());
        existingStatus.setStatusColorHex(req.getStatusColorHex());
        existingStatus.setSortOrder(req.getSortOrder());
        existingStatus.setActive(req.getActive());

        return new StatusResponse(_productStatusRepository.save(existingStatus));
    }

    public boolean deleteStatus(Long id) {
        if (id == null) {
            return false;
        }
        _productStatusRepository.deleteById(id);
        return true;
    }

}
