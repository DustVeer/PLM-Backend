package com.plm.poelman.java_api.services;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.Workflow;
import com.plm.poelman.java_api.models.WorkflowStatus;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.models.dto.workflows.CreateUpdateWorkflowRequest;
import com.plm.poelman.java_api.models.dto.workflows.WorkflowResponse;
import com.plm.poelman.java_api.repositories.ProductStatusRepository;
import com.plm.poelman.java_api.repositories.UserRepository;
import com.plm.poelman.java_api.repositories.WorkflowRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkflowService {

    private final WorkflowRepository _workflowRepository;
    private final UserRepository _userRepository;
    private final ProductStatusRepository _statusRepository;

    public WorkflowService(WorkflowRepository workflowRepository, UserRepository userRepository,
            ProductStatusRepository statusRepository) {
        this._workflowRepository = workflowRepository;
        this._userRepository = userRepository;
        this._statusRepository = statusRepository;
    }

    @Transactional
    public List<WorkflowResponse> getAllWorkflowsWithStatuses() {
        return _workflowRepository.findAllWithStatuses().stream()
                .map((w) -> {
                    UserResponse createdBy = new UserResponse(w.getCreatedBy());
                    UserResponse updatedBy = new UserResponse(w.getUpdatedBy());
                    List<StatusResponse> statuses = w.getWorkflowStatuses().stream()
                            .map(ws -> new StatusResponse(ws.getStatus()))
                            .toList();
                    return new WorkflowResponse(w, createdBy, updatedBy, statuses);
                })
                .toList();
    }

    @Transactional
    public WorkflowResponse getWorkflowByIdWithStatuses(Long id) {
        if (id == null) {
            return null;
        }

        Optional<Workflow> optionalWorkflow = _workflowRepository.findByIdWithStatuses(id);
        if (optionalWorkflow.isEmpty()) {
            return null;
        }
        Workflow workflow = optionalWorkflow.get();

        UserResponse createdBy = new UserResponse(workflow.getCreatedBy());
        UserResponse updatedBy = new UserResponse(workflow.getUpdatedBy());
        List<StatusResponse> statuses = workflow.getWorkflowStatuses().stream()
                .map(ws -> new StatusResponse(ws.getStatus()))
                .toList();

        return new WorkflowResponse(workflow, createdBy, updatedBy, statuses);
    }

    @Transactional
    public WorkflowResponse createWorkflow(CreateUpdateWorkflowRequest req) {

        User createdBy = _userRepository.findById(req.getCreatedById()).orElse(null);

        if (createdBy == null) {
            return null;
        }


        LocalDateTime now = LocalDateTime.now();

        Workflow newWorkflow = new Workflow();
        newWorkflow.setName(req.getName());
        newWorkflow.setDescription(req.getDescription());
        newWorkflow.setIsActive(req.getIsActive());
        newWorkflow.setIsDefault(req.getIsDefault());
        newWorkflow.setCreatedBy(createdBy);
        newWorkflow.setUpdatedBy(createdBy);
        newWorkflow.setCreatedAt(now);
        newWorkflow.setUpdatedAt(now);

        if (req.getStatuses() != null) {
            List<ProductStatus> statuses = _statusRepository
                    .findByIdIn(req.getStatuses().stream().map(s -> s.getStatusId()).toList());
            newWorkflow.clearWorkflowStatuses();

            for (int i = 0; i < statuses.size(); i++) {
                WorkflowStatus ws = new WorkflowStatus();
                ws.setWorkflow(newWorkflow);
                ws.setStatus(statuses.get(i));
                ws.setSortOrder(req.getStatuses().get(i).getSortOrder());
                newWorkflow.addWorkflowStatus(ws);
            }
        }

        Workflow savedWorkflow = _workflowRepository.save(newWorkflow);
        List<StatusResponse> statusResponses = savedWorkflow.getWorkflowStatuses().stream()
                .map(ws -> new StatusResponse(ws.getStatus()))
                .toList();

        return new WorkflowResponse(savedWorkflow, new UserResponse(createdBy), new UserResponse(createdBy),
                statusResponses);
    }

    @Transactional
    public WorkflowResponse updateWorkflow(Long id, CreateUpdateWorkflowRequest req) {

        Workflow toUpdatedWorkflow = _workflowRepository.findById(id).orElse(null);
        if (toUpdatedWorkflow == null) {
            return null;
        }

        User updatedBy = _userRepository.findById(req.getUpdatedById()).orElse(null);
        if (updatedBy == null) {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();

        toUpdatedWorkflow.setName(req.getName());
        toUpdatedWorkflow.setDescription(req.getDescription());
        toUpdatedWorkflow.setIsActive(req.getIsActive());
        toUpdatedWorkflow.setIsDefault(req.getIsDefault());
        toUpdatedWorkflow.setUpdatedBy(updatedBy);
        toUpdatedWorkflow.setUpdatedAt(now);

        if (req.getStatuses() != null) {
            List<ProductStatus> statuses = _statusRepository
                    .findByIdIn(req.getStatuses().stream().map(s -> s.getStatusId()).toList());
            toUpdatedWorkflow.clearWorkflowStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                WorkflowStatus ws = new WorkflowStatus();
                ws.setWorkflow(toUpdatedWorkflow);
                ws.setStatus(statuses.get(i));
                ws.setSortOrder(req.getStatuses().get(i).getSortOrder());
                toUpdatedWorkflow.addWorkflowStatus(ws);
            }
        }

        Workflow savedWorkflow = _workflowRepository.save(toUpdatedWorkflow);
        List<StatusResponse> statusResponses = savedWorkflow.getWorkflowStatuses().stream()
                .map(ws -> new StatusResponse(ws.getStatus()))
                .toList();

        return new WorkflowResponse(savedWorkflow, new UserResponse(toUpdatedWorkflow.getCreatedBy()),
                new UserResponse(updatedBy), statusResponses);
    }

    @Transactional
    public WorkflowResponse toggleActiveById(Long id) {
        Workflow workflow = _workflowRepository.findById(id).orElse(null);
        if (workflow == null) {
            return null;
        }

        workflow.setIsActive(workflow.getIsActive() == 0 ? 1 : 0);
        Workflow savedWorkflow = _workflowRepository.save(workflow);

        return this.mapToWorkflowResponse(savedWorkflow);
    }

    @Transactional
    public boolean deleteWorkflow(Long id) {
        Workflow workflow = _workflowRepository.findById(id).orElse(null);
        if (workflow == null) {
            return false;
        }

        _workflowRepository.delete(workflow);
        return true;
    }

    private WorkflowResponse mapToWorkflowResponse(Workflow workflow) {
        UserResponse createdBy = new UserResponse(workflow.getCreatedBy());
        UserResponse updatedBy = new UserResponse(workflow.getUpdatedBy());
        List<StatusResponse> statuses = workflow.getWorkflowStatuses().stream()
                .map(ws -> new StatusResponse(ws.getStatus()))
                .toList();

        return new WorkflowResponse(workflow, createdBy, updatedBy, statuses);
    }

}
