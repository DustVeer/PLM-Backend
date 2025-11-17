package com.plm.poelman.java_api.models.dto.statuses;


import com.plm.poelman.java_api.models.ProductStatus;

public class StatusResponse {
    private Long id;
    private String name;
    private String description;
    private String colorHexCode;

    public StatusResponse(ProductStatus status) {
        this.name = status.getName();
        this.id = status.getId();
        this.description = status.getDescription();
        this.colorHexCode = status.getStatusColorHex();
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
    public String getColorHexCode() {
        return colorHexCode;
    }
    public void setColorHexCode(String colorHexCode) {
        this.colorHexCode = colorHexCode;
    }

}
