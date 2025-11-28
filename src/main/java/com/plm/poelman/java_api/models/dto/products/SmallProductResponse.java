package com.plm.poelman.java_api.models.dto.products;

import java.time.LocalDateTime;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.ProductStatus;

public class SmallProductResponse {
    private Long id;
    private String name;
    private String description;
    private String statusName;
    private String statusColorHex;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SmallProductResponse(Long id, String name, String description, String statusName, String statusColorHex, String categoryName,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.statusName = statusName;
        this.statusColorHex = statusColorHex;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public SmallProductResponse(Product product, ProductStatus status) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.statusName = status.getName();
        this.statusColorHex = status.getStatusColorHex();
        this.categoryName = product.getCategory().getName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public SmallProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColorHex() {
        return statusColorHex;
    }

    public void setStatusColorHex(String statusColorHex) {
        this.statusColorHex = statusColorHex;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
