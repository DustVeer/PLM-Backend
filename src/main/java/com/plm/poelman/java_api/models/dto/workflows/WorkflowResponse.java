package com.plm.poelman.java_api.models.dto.workflows;

import java.time.LocalDateTime;
import java.util.List;

import com.plm.poelman.java_api.models.Workflow;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.models.dto.users.UserResponse;

public class WorkflowResponse {
    private Long id;
    private String name;
    private String description;
    private Integer isActive;
    private Integer isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse createdBy;
    private UserResponse updatedBy;
    private List<StatusResponse> statuses;

    public WorkflowResponse() {}

    public WorkflowResponse(Workflow workflow, UserResponse createdBy, UserResponse updatedBy, List<StatusResponse> statuses) {
        this.id = workflow.getId();
        this.name = workflow.getName();
        this.description = workflow.getDescription();
        this.isActive = workflow.getIsActive();
        this.isDefault = workflow.getIsDefault();
        this.createdAt = workflow.getCreatedAt();
        this.updatedAt = workflow.getUpdatedAt();
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.statuses = statuses;
    }

    public WorkflowResponse(Long id, String name, String description, Integer isActive, Integer isDefault, LocalDateTime createdAt, LocalDateTime updatedAt, UserResponse createdBy, UserResponse updatedBy, List<StatusResponse> statuses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.statuses = statuses;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getIsActive() {
        return isActive;
    }
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Integer getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserResponse getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponse createdBy) {
        this.createdBy = createdBy;
    }

    public UserResponse getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserResponse updatedBy) {
        this.updatedBy = updatedBy;
    }
    public List<StatusResponse> getStatuses() {
        return statuses;
    }
    public void setStatuses(List<StatusResponse> statuses) {
        this.statuses = statuses;
    }

    

    
}
