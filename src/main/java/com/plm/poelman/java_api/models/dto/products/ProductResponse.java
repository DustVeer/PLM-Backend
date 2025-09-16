package com.plm.poelman.java_api.models.dto.products;

import java.time.LocalDateTime;

import com.plm.poelman.java_api.models.Product;
import com.plm.poelman.java_api.models.ProductCategory;
import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.User;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;

    private ProductCategory productCategory;
    private User createdBy;
    private ProductStatus productStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponse(Product product, ProductCategory productCategory, User createdBy, ProductStatus productStatus) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.productCategory = productCategory;
        this.createdBy = createdBy;
        this.productStatus = productStatus;
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public User getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public ProductStatus getProductStatus() {
        return this.productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
