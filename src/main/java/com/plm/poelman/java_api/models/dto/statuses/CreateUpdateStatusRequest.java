package com.plm.poelman.java_api.models.dto.statuses;

import java.util.List;

public class CreateUpdateStatusRequest {
    private String name;
    private String description;
    private String statusColorHex;
    private Long sortOrder;
    private Integer isActive;
    private List<Long> requiredFieldIds;

    public CreateUpdateStatusRequest() {}

    public CreateUpdateStatusRequest(String name, String description, String statusColorHex, Long sortOrder, Integer isActive, List<Long> requiredFieldIds) {
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.requiredFieldIds = requiredFieldIds;
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
    public List<Long> getRequiredFieldIds() {
        return requiredFieldIds;
    }
    public void setRequiredFieldIds(List<Long> requiredFieldIds) {
        this.requiredFieldIds = requiredFieldIds;
    }

}
