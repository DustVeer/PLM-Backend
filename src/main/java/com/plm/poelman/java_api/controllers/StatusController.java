package com.plm.poelman.java_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.dto.statuses.CreateUpdateStatusRequest;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.services.StatusService;


@RestController
@RequestMapping("/api/statuses")
public class StatusController {
    
    public final StatusService _statusService;

    public StatusController(StatusService statusService) {
        this._statusService = statusService;
    }

    @GetMapping
    public List<StatusResponse> getStatuses() {
        return _statusService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public StatusResponse getStatusById(@PathVariable Long id) {
        return _statusService.getStatusById(id);
    }

    @GetMapping("/active")
    public List<StatusResponse> getAllActiveStatuses() {
        return _statusService.getAllActiveStatuses();
    }

    @PostMapping
    public ResponseEntity<?> createStatus(@RequestBody CreateUpdateStatusRequest req) {
        ResponseEntity<?> validationResponse = validateStatusRequest(req);
        if (validationResponse != null) {
            return validationResponse;
        }
        if(_statusService.getByName(req.getName()) != null) {
            return ResponseEntity.badRequest().body("Status with the same name already exists");
        }
        if(_statusService.getBySortOrder(req.getSortOrder()) != null) {
            return ResponseEntity.badRequest().body("Status with the same sort order already exists");
        }

        ProductStatus newStatus = new ProductStatus(req.getName(), req.getDescription(), req.getStatusColorHex(), req.getSortOrder(), req.getActive());
        StatusResponse dto = _statusService.createStatus(newStatus);

        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody CreateUpdateStatusRequest req) {
        ResponseEntity<?> validationResponse = validateStatusRequest(req);
        if (validationResponse != null) {
            return validationResponse;
        }

        return ResponseEntity.ok().body( _statusService.updateStatus(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Id is missing");
        }
        if(!_statusService.deleteStatus(id)) {
            return ResponseEntity.status(500).body("Failed to delete status");
        }

        return ResponseEntity.ok().body("Deleted status with id: " + id + " successfully");
    }

    private ResponseEntity<?> validateStatusRequest(CreateUpdateStatusRequest req) {
        if(req == null) {
            return ResponseEntity.badRequest().body("Request body is missing");
        }
        if(req.getName() == null || req.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Name is missing");
        }
        if(req.getDescription() == null || req.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().body("Description is missing");
        }
        if(req.getStatusColorHex() == null || req.getStatusColorHex().isEmpty()) {
            return ResponseEntity.badRequest().body("StatusColorHex is missing");
        }
        if(req.getSortOrder() == null || req.getSortOrder() == 0) {
            return ResponseEntity.badRequest().body("SortOrder is missing");
        }
        return null;
    }

    

}
