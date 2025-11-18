package com.plm.poelman.java_api.models.dto.users;

import java.time.LocalDateTime;

import com.plm.poelman.java_api.models.User;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Long roleId;
    private LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roleId = user.getRole().getId();
        this.createdAt = user.getCreatedAt();
    }

    public UserResponse(Long id, String name, String email, Long roleId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
        this.createdAt = createdAt;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
