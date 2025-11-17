package com.plm.poelman.java_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
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
}
