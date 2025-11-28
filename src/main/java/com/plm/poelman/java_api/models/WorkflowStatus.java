package com.plm.poelman.java_api.models;


import com.plm.poelman.java_api.models.IDs.WorkflowStatusId;

import jakarta.persistence.*;

@Entity
@Table(name = "WorkflowStatuses", schema = "plm")
public class WorkflowStatus {

    @EmbeddedId
    private WorkflowStatusId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workflowId")
    @JoinColumn(name = "WorkflowID", nullable = false)
    private Workflow workflow;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusId")
    @JoinColumn(name = "StatusID", nullable = false)
    private ProductStatus status; // Assuming you already have ProductStatus entity

    @Column(name = "SortOrder", nullable = false)
    private int sortOrder;

    public WorkflowStatus() {
    }

    public WorkflowStatus(Workflow workflow, ProductStatus status, int sortOrder) {
        this.workflow = workflow;
        this.status = status;
        this.id = new WorkflowStatusId(
                workflow != null ? workflow.getId() : null,
                status != null ? status.getId() : null
        );
        this.sortOrder = sortOrder;
    }

    public WorkflowStatusId getId() {
        return id;
    }

    public void setId(WorkflowStatusId id) {
        this.id = id;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
        if (workflow != null) {
            if (this.id == null) {
                this.id = new WorkflowStatusId();
            }
            this.id.setWorkflowId(workflow.getId());
        }
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
        if (status != null) {
            if (this.id == null) {
                this.id = new WorkflowStatusId();
            }
            this.id.setStatusId(status.getId());
        }
    }
    public int getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
