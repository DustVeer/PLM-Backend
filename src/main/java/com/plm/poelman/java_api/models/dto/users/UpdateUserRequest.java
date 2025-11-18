package com.plm.poelman.java_api.models.dto.users;

public class UpdateUserRequest {
    private String name;
    private String email;
    private int roleId;

    public UpdateUserRequest(String name, String email, int roleId) {
        this.name = name;
        this.email = email;
        this.roleId = roleId;

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
