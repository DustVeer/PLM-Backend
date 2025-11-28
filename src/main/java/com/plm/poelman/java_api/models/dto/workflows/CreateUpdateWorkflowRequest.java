package com.plm.poelman.java_api.models.dto.workflows;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;



public class CreateUpdateWorkflowRequest {
    private String name;
    private String description;
    private Integer isActive;
    private Integer isDefault;
    private Long createdById;
    private Long updatedById;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<WorkflowStatusRequest> statuses;

    public CreateUpdateWorkflowRequest() {
    }


    public CreateUpdateWorkflowRequest(String name, String description, Integer isActive, Integer isDefault, Long createdById, Long updatedById,
            LocalDateTime createdAt, LocalDateTime updatedAt, List<WorkflowStatusRequest> statuses) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.isDefault = isDefault;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statuses = statuses;
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
    public Long getCreatedById() {
        return createdById;
    }
    public Long getUpdatedById() {
        return updatedById;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public List<WorkflowStatusRequest> getStatuses() {
        return statuses;
    }
    public void setStatuses(List<WorkflowStatusRequest> statuses) {
        this.statuses = statuses;
    }
    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }   
    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}