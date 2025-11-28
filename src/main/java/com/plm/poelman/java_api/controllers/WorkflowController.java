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

import com.plm.poelman.java_api.models.dto.workflows.CreateUpdateWorkflowRequest;
import com.plm.poelman.java_api.models.dto.workflows.WorkflowResponse;
import com.plm.poelman.java_api.services.WorkflowService;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {
    
    private final WorkflowService _workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this._workflowService = workflowService;
    }

    @GetMapping
    public List<WorkflowResponse> getAllWorkflows() {
        return _workflowService.getAllWorkflowsWithStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkflowById(@PathVariable Long id) {
        if(id == null) {
            return ResponseEntity.badRequest().body("Invalid workflow ID");
        }

        WorkflowResponse workflowResponse = _workflowService.getWorkflowByIdWithStatuses(id);
        if(workflowResponse == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(workflowResponse);
    }

    @PostMapping
    public ResponseEntity<?> createWorkflow(@RequestBody CreateUpdateWorkflowRequest req) {
        if(req == null) {
            return ResponseEntity.status(400).body("Request body is missing");
        }
        WorkflowResponse createdWorkflow = _workflowService.createWorkflow(req);
        if (createdWorkflow == null) {
            return ResponseEntity.status(400).body("Failed to create workflow");
        }
        return ResponseEntity.ok(createdWorkflow);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkflow(@PathVariable Long id, @RequestBody CreateUpdateWorkflowRequest req) {
        if(req == null) {
            return ResponseEntity.status(400).body("Request body is missing");
        }
        WorkflowResponse updatedWorkflow = _workflowService.updateWorkflow(id, req);
        if (updatedWorkflow == null) {
            return ResponseEntity.status(400).body("Failed to update workflow");
        }
        return ResponseEntity.ok(updatedWorkflow);
    }

    @PutMapping("/{id}/toggle-active")
    public ResponseEntity<?> toggleActive(@PathVariable Long id) {
        System.out.println("Toggling active status for workflow with ID: " + id);
        WorkflowResponse workflowToToggleActive = _workflowService.toggleActiveById(id);
        if (workflowToToggleActive == null) {
            return ResponseEntity.status(400).body("Failed to toggle active status");
        }
        return ResponseEntity.ok(workflowToToggleActive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkflow(@PathVariable Long id) {
        boolean deleted = _workflowService.deleteWorkflow(id);
        if (!deleted) {
            return ResponseEntity.status(400).body("Failed to delete workflow");
        }
        return ResponseEntity.ok().body("Workflow with id:" + id + " deleted successfully");
    }
}
