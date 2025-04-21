package com.auth.auth_service.model.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    NORMAL("ROLE_NORMAL");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
