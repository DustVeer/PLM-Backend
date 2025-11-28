package com.plm.poelman.java_api.models;

import com.plm.poelman.java_api.models.IDs.StatusRequiredFieldId;
import jakarta.persistence.*;

@Entity
@Table(name = "StatusRequiredFields", schema = "plm")
public class StatusRequiredField {

    @EmbeddedId
    private StatusRequiredFieldId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("statusId")
    @JoinColumn(name = "StatusID", nullable = false)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("requiredFieldId")
    @JoinColumn(name = "RequiredFieldID", nullable = false)
    private RequiredField requiredField;

    public StatusRequiredField() {
    }

    public StatusRequiredField(ProductStatus status, RequiredField requiredField) {
        this.status = status;
        this.requiredField = requiredField;
        this.id = new StatusRequiredFieldId(status.getId(), requiredField.getId());
    }

    public StatusRequiredFieldId getId() {
        return id;
    }

    public void setId(StatusRequiredFieldId id) {
        this.id = id;

    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
        if (status != null) {
            if (this.id == null) {
                this.id = new StatusRequiredFieldId();
            }
            this.id.setStatusId(status.getId());
        }
    }

    public RequiredField getRequiredField() {
        return requiredField;
    }

    public void setRequiredField(RequiredField requiredField) {
        this.requiredField = requiredField;
        if (requiredField != null) {
            if (this.id == null) {
                this.id = new StatusRequiredFieldId();
            }
            this.id.setRequiredFieldId(requiredField.getId());
        }
    }
}
