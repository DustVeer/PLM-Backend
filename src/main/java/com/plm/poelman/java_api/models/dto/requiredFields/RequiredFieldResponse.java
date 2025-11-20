package com.plm.poelman.java_api.models.dto.requiredFields;

import com.plm.poelman.java_api.models.RequiredField;

public class RequiredFieldResponse {

    private Long id;
    private String fieldKey;

    public RequiredFieldResponse() {}

    public RequiredFieldResponse(RequiredField requiredField) {
        this.fieldKey = requiredField.getFieldKey();
        this.id = requiredField.getId();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFieldKey() {
        return fieldKey;
    }
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}
