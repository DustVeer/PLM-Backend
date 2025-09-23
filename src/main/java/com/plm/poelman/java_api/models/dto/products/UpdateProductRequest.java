package com.plm.poelman.java_api.models.dto.products;


public class UpdateProductRequest {
    private String name;
    private String description;

    private Long productCategoryId;
    private Long productStatusId;

    
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
    public Long getProductCategoryId() {
        return this.productCategoryId;
    }
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }
    public Long getProductStatusId() {
        return this.productStatusId;
    }
    public void setProductStatusId(Long productStatusId) {
        this.productStatusId = productStatusId;
    }
}
