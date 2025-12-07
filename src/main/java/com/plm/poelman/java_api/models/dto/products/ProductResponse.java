package com.plm.poelman.java_api.models.dto.products;

import java.time.LocalDateTime;


import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.dto.categories.CategoryResponse;
import com.plm.poelman.java_api.models.dto.statuses.StatusResponse;
import com.plm.poelman.java_api.models.dto.users.UserResponse;
import com.plm.poelman.java_api.models.dto.workflows.WorkflowResponse;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private String colour;
    private Float price;

    private CategoryResponse productCategory;
    private UserResponse createdBy;
    private UserResponse updatedBy;
    private StatusResponse productStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private WorkflowResponse workflow;
    
    
    public ProductResponse(Product product, CategoryResponse productCategory, UserResponse createdBy, UserResponse updatedBy,
            StatusResponse productStatus, WorkflowResponse workflow) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.colour = product.getColour();
        this.price = product.getPrice();
        this.productCategory = productCategory;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.productStatus = productStatus;
        this.workflow = workflow;
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.colour = product.getColour();
        this.price = product.getPrice();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
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

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public CategoryResponse getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(CategoryResponse productCategory) {
        this.productCategory = productCategory;
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

    public StatusResponse getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(StatusResponse productStatus) {
        this.productStatus = productStatus;
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

    public WorkflowResponse getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowResponse workflow) {
        this.workflow = workflow;
    }
}
