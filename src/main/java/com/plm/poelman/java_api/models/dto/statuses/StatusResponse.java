package com.plm.poelman.java_api.models.dto.statuses;


import java.util.List;

import com.plm.poelman.java_api.models.ProductStatus;
import com.plm.poelman.java_api.models.dto.requiredFields.RequiredFieldResponse;


public class StatusResponse {
    private Long id;
    private String name;
    private String description;
    private String statusColorHex;
    private Long sortOrder;
    private Integer isActive;
    private List<RequiredFieldResponse> requiredFields;

    public StatusResponse() {}

    public StatusResponse(ProductStatus status) {
        this.name = status.getName();
        this.id = status.getId();
        this.description = status.getDescription();
        this.statusColorHex = status.getStatusColorHex();
        this.sortOrder = status.getSortOrder();
        this.isActive = status.getIsActive();
        this.requiredFields = status.getStatusRequiredFields().stream().map(rf -> new RequiredFieldResponse(rf.getRequiredField())).toList();
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
    public String getStatusColorHex() {
        return statusColorHex;
    }
    public void setStatusColorHex(String statusColorHex) {
        this.statusColorHex = statusColorHex;
    }
    public Long getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }
    public Integer getIsActive() {
        return isActive;
    }
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public List<RequiredFieldResponse> getRequiredFields() {
        return requiredFields;
    }

    public void setRequiredFields(List<RequiredFieldResponse> requiredFields) {
        this.requiredFields = requiredFields;
    }
}
