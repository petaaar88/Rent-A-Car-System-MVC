package com.metropolitan.rent_a_car_system.enums;

public enum EngineType {
    DIESEL("Diesel"),
    PETROL("Petrol"),
    ELECTRIC("Electric"),
    HYBRID("Hybrid");

    private final String displayName;

    EngineType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

