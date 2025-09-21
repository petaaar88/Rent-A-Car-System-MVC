package com.metropolitan.rent_a_car_system.models;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Component
@SessionScope
public class SessionUser {
    private UserRole role;
    private UUID userId;

    public boolean isLoggedIn() {
        return role != null;
    }

    public void logout() {
        role = null;
        userId = null;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }


}
