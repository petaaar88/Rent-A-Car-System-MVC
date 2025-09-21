package com.metropolitan.rent_a_car_system.dto;

import java.util.UUID;

public class CarOverview {
    public UUID id;
    public String brand;
    public String model;
    public String engineType;
    public String category;

    public CarOverview(UUID id, String brand, String model, String engineType, String category) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
        this.category = category;
    }

}
