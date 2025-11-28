package com.plm.poelman.java_api.models.dto.workflows;

public class WorkflowStatusRequest {
    private Long statusId;
    private int sortOrder;

    public WorkflowStatusRequest() {
    }
    public WorkflowStatusRequest(Long statusId, int sortOrder) {
        this.statusId = statusId;
        this.sortOrder = sortOrder;
    }
    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    public int getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
