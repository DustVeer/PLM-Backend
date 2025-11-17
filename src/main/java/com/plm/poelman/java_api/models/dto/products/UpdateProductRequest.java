package com.plm.poelman.java_api.models.dto.products;


public class UpdateProductRequest {
    private String name;
    private String description;

    private Long categoryId;
    private Long statusId;

    
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
        return this.categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Long getStatusId() {
        return this.statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
