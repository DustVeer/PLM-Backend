package com.plm.poelman.java_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "Active", length = 100)
    private Integer active;

    public ProductStatus() {
        // JPA requires a no-arg constructor
    }

    public ProductStatus(String name, String description, String statusColorHex, Long sortOrder, Integer active) {
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.active = active;
    }

    public ProductStatus(Long id, String name, String description, String statusColorHex, Long sortOrder, Integer active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.statusColorHex = statusColorHex;
        this.sortOrder = sortOrder;
        this.active = active;
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
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }

}

