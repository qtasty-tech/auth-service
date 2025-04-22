package com.auth.auth_service.model.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    RESTAURANT_ADMIN("ROLE_RESTAURANT_ADMIN"),
    DELIVERY_PERSONNEL("ROLE_DELIVERY_PERSONNEL");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
