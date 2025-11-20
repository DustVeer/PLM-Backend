package com.plm.poelman.java_api.models.IDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StatusRequiredFieldId implements Serializable {

    @Column(name = "StatusID")
    private Long statusId;

    @Column(name = "RequiredFieldID")
    private Long requiredFieldId;

    public StatusRequiredFieldId() {}

    public StatusRequiredFieldId(Long statusId, Long requiredFieldId) {
        this.statusId = statusId;
        this.requiredFieldId = requiredFieldId;
    }

    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getRequiredFieldId() {
        return requiredFieldId;
    }
    public void setRequiredFieldId(Long requiredFieldId) {
        this.requiredFieldId = requiredFieldId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId, requiredFieldId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusRequiredFieldId)) return false;
        StatusRequiredFieldId that = (StatusRequiredFieldId) o;
        return Objects.equals(statusId, that.statusId) &&
               Objects.equals(requiredFieldId, that.requiredFieldId);
    }
}
