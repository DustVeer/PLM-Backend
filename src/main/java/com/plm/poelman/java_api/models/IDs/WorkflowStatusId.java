package com.plm.poelman.java_api.models.IDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class WorkflowStatusId implements Serializable {

    @Column(name = "WorkflowID")
    private Long workflowId;

    @Column(name = "StatusID")
    private Long statusId;

    public WorkflowStatusId() {
    }

    public WorkflowStatusId(Long workflowId, Long statusId) {
        this.workflowId = workflowId;
        this.statusId = statusId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkflowStatusId)) return false;
        WorkflowStatusId that = (WorkflowStatusId) o;
        return Objects.equals(workflowId, that.workflowId) &&
               Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workflowId, statusId);
    }
}
