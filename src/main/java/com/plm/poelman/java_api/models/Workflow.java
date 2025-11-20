package com.plm.poelman.java_api.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Workflows", schema = "plm")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkflowID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "IsActive", nullable = false)
    private Integer isActive;

    @Column(name = "IsDefault", nullable = false)
    private Integer isDefault;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UpdatedAt", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedBy", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UpdatedBy", nullable = false)
    private User updatedBy;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowStatus> workflowStatuses = new ArrayList<>();

    /* Constructors */

    public Workflow() {
    }

    public Workflow(String name,  String description, Integer isActive, User createdBy, User updatedBy, Integer isDefault) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDefault = isDefault;
    }

    /* Getters and setters */

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
    public Integer getIsActive() {
        return isActive;
    }
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
    public Integer getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<WorkflowStatus> getWorkflowStatuses() {
        return workflowStatuses;
    }

    public void setWorkflowStatuses(List<WorkflowStatus> workflowStatuses) {
        this.workflowStatuses = workflowStatuses;
    }

    /* Helper methods */

    public void addWorkflowStatus(WorkflowStatus workflowStatus) {
        workflowStatuses.add(workflowStatus);
        workflowStatus.setWorkflow(this);
    }

    public void removeWorkflowStatus(WorkflowStatus workflowStatus) {
        workflowStatuses.remove(workflowStatus);
        workflowStatus.setWorkflow(null);
    }

    public void clearWorkflowStatuses() {
        for (WorkflowStatus ws : new ArrayList<>(workflowStatuses)) {
            removeWorkflowStatus(ws);
        }
    }
}
