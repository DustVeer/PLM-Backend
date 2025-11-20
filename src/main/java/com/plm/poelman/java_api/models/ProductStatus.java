package com.plm.poelman.java_api.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProductStatuses", schema = "plm")
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private Long id;

    @Column(name = "Name", nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "Description", length = 4000)
    private String description;
    
    @Column(name = "StatusColorHex", length = 100)
    private String statusColorHex;

    @Column(name = "SortOrder", length = 100)
    private Long sortOrder;

    @Column(name = "IsActive", length = 100)
    private Integer isActive;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusRequiredField> statusRequiredFields = new ArrayList<>();

    public ProductStatus() {
        // JPA requires a no-arg constructor
    }

    public ProductStatus(String name, String description, String statusColorHex, Long sortOrder, Integer isActive, List<StatusRequiredField> statusRequiredFields) {
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.isActive = isActive;   
        this.statusRequiredFields = statusRequiredFields;
    }

    public ProductStatus(Long id, String name, String description, String statusColorHex, Long sortOrder, Integer isActive, List<StatusRequiredField> statusRequiredFields) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.isActive = isActive;
        this.statusRequiredFields = statusRequiredFields;
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
    public List<StatusRequiredField> getStatusRequiredFields() {
        return statusRequiredFields;
    }
    public void setStatusRequiredFields(List<StatusRequiredField> statusRequiredFields) {
        this.statusRequiredFields = statusRequiredFields;
    }
    public void addStatusRequiredField(StatusRequiredField statusRequiredField) {
        this.statusRequiredFields.add(statusRequiredField);
    }
    public void removeStatusRequiredField(StatusRequiredField statusRequiredField) {
        this.statusRequiredFields.remove(statusRequiredField);
    }
    public void clearStatusRequiredFields() {
        this.statusRequiredFields.clear();
    }

    

}

