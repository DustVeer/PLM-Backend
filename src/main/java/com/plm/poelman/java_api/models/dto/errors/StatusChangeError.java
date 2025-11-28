package com.plm.poelman.java_api.models.dto.errors;

import java.util.List;

public class StatusChangeError {
    private String message;
    private List<String> missingRequiredFields;

    public StatusChangeError(String message, List<String> missingRequiredFields) {
        this.message = message;
        this.missingRequiredFields = missingRequiredFields;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<String> getMissingRequiredFields() {
        return missingRequiredFields;
    }
    public void setMissingRequiredFields(List<String> missingRequiredFields) {
        this.missingRequiredFields = missingRequiredFields;
    }
}
