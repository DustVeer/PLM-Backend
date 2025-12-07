package com.plm.poelman.java_api.models.dto.login;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.userRoles.UserRoleResponse;

public class LoginResponse {

    private Long userId;
    private String userName;
    private String userEmail;
    private UserRoleResponse userRoleResponse;
    private String token;

    public LoginResponse(Long userId, String userName, String userEmail, UserRoleResponse userRoleResponse,
            String token) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRoleResponse = userRoleResponse;
        this.token = token;
    }

    public LoginResponse(User user, String token) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userEmail = user.getEmail();
        this.userRoleResponse = new UserRoleResponse(user.getRole().getId(), user.getRole().getName());
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UserRoleResponse getUserRoleResponse() {
        return userRoleResponse;
    }

    public void setUserRoleResponse(UserRoleResponse userRoleResponse) {
        this.userRoleResponse = userRoleResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
