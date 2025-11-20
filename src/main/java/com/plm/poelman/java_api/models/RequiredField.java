package com.plm.poelman.java_api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "RequiredFields", schema = "plm")
public class RequiredField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RequiredFieldID")
    private Long id;

    @Column(name = "FieldKey", nullable = false, length = 100)
    private String fieldKey;

    public RequiredField() {}

    public RequiredField(String fieldKey) {
        this.fieldKey = fieldKey;
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
