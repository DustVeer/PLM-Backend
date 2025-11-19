package com.plm.poelman.java_api.models.dto.statuses;

public class CreateUpdateStatusRequest {
    private String name;
    private String description;
    private String statusColorHex;
    private Long sortOrder;
    private Integer active;

    public CreateUpdateStatusRequest() {}

    public CreateUpdateStatusRequest(String name, String description, String statusColorHex, Long sortOrder, Integer active) {
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.active = active;
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
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }

}
