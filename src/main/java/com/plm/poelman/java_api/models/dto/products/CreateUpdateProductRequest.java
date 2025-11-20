package com.plm.poelman.java_api.models.dto.products;

import java.time.LocalDateTime;

public class CreateUpdateProductRequest {
    private String name;
    private String description;
    private Long categoryId;
    private Long createdById;
    private Long updatedById;
    private Long statusId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    public CreateUpdateProductRequest() {
    }

    
    public CreateUpdateProductRequest(String name, String description, Long categoryId, Long createdById, Long updatedById,
            Long statusId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.createdById = createdById;
        this.updatedById = updatedById;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getCreatedById() {
        return createdById;
    }
   
    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    public Long getUpdatedById() {
        return updatedById;
    }
    public void setUpdatedById(Long updatedById) {
        this.updatedById = updatedById;
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

}
