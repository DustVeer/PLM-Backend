package com.plm.poelman.java_api.services;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.RequiredField;
import com.plm.poelman.java_api.models.StatusRequiredField;
import com.plm.poelman.java_api.models.dto.requiredFields.RequiredFieldResponse;
import com.plm.poelman.java_api.models.dto.statuses.CreateUpdateStatusRequest;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.repositories.ProductStatusRepository;
import com.plm.poelman.java_api.repositories.RequiredFieldRepository;

@Service
public class StatusService {

    private final ProductStatusRepository _productStatusRepository;
    private final RequiredFieldRepository _requiredFieldRepository;

    public StatusService(ProductStatusRepository productStatusRepository,
            RequiredFieldRepository requiredFieldRepository) {
        this._productStatusRepository = productStatusRepository;
        this._requiredFieldRepository = requiredFieldRepository;
    }

    public List<StatusResponse> getAllStatuses() {
        return _productStatusRepository.findAll().stream()
                .map((ps) -> new StatusResponse(ps))
                .toList();
    }

    public StatusResponse getStatusById(Long id) {
        if (id == null) {
            return null;
        }

        return _productStatusRepository.findById(id)
                .map((ps) -> new StatusResponse(ps))
                .orElse(null);
    }

    public StatusResponse getByName(String name) {
        if (name == null) {
            return null;
        }
        Optional<ProductStatus> status = _productStatusRepository.findByName(name);
        if (status.isEmpty()) {
            return null;
        }
        return new StatusResponse(status.get());
    }

    public StatusResponse getBySortOrder(Long sortOrder) {
        if (sortOrder == null) {
            return null;
        }
        Optional<ProductStatus> status = _productStatusRepository.findBySortOrder(sortOrder);
        System.out.println("Status:" + status);
        if (status.isEmpty()) {
            return null;
        }
        return new StatusResponse(status.get());
    }

    public List<StatusResponse> getAllActiveStatuses() {
        return _productStatusRepository.findByIsActive(1).stream()
                .map((ps) -> new StatusResponse(ps))
                .toList();
    }

    public StatusResponse createStatus(CreateUpdateStatusRequest req) {

        ProductStatus newStatus = new ProductStatus();
        newStatus.setName(req.getName());
        newStatus.setDescription(req.getDescription());
        newStatus.setStatusColorHex(req.getStatusColorHex());
        newStatus.setSortOrder(req.getSortOrder());
        newStatus.setIsActive(req.getIsActive());

        if (req.getRequiredFieldIds() != null) {
            List<RequiredField> requiredFields = _requiredFieldRepository.findAllById(req.getRequiredFieldIds());
            for (RequiredField rf : requiredFields) {
                StatusRequiredField srf = new StatusRequiredField();
                srf.setRequiredField(rf);
                srf.setStatus(newStatus);
                newStatus.addStatusRequiredField(srf);
            }
        }

        ProductStatus saveProductStatus = _productStatusRepository.save(newStatus);

        return new StatusResponse(saveProductStatus);
    }

    public StatusResponse updateStatus(Long id, CreateUpdateStatusRequest req) {
        if (id == null || req == null) {
            return null;
        }

        Optional<ProductStatus> existingStatusOpt = _productStatusRepository.findById(id);
        if (existingStatusOpt.isEmpty()) {
            return null;
        }

        System.out.println("Req:" + req.getRequiredFieldIds());

        ProductStatus existingStatus = existingStatusOpt.get();
        existingStatus.setName(req.getName());
        existingStatus.setDescription(req.getDescription());
        existingStatus.setStatusColorHex(req.getStatusColorHex());
        existingStatus.setSortOrder(req.getSortOrder());
        existingStatus.setIsActive(req.getIsActive());

        if (req.getRequiredFieldIds() != null) {
            List<RequiredField> requiredFields = _requiredFieldRepository.findAllById(req.getRequiredFieldIds());
            existingStatus.clearStatusRequiredFields();

            for (RequiredField rf : requiredFields) {
                StatusRequiredField srf = new StatusRequiredField();
                srf.setRequiredField(rf);
                srf.setStatus(existingStatus);
                existingStatus.addStatusRequiredField(srf);
            }
        }

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
