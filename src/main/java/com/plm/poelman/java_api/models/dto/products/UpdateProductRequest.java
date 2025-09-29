package com.plm.poelman.java_api.models.dto.products;


public class UpdateProductRequest {
    private String name;
    private String description;

    private Long CategoryId;
    private Long StatusId;

    
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
    public Long getCategoryId() {
        return this.CategoryId;
    }
    public void setCategoryId(Long CategoryId) {
        this.CategoryId = CategoryId;
    }
    public Long getStatusId() {
        return this.StatusId;
    }
    public void setStatusId(Long StatusId) {
        this.StatusId = StatusId;
    }
}
