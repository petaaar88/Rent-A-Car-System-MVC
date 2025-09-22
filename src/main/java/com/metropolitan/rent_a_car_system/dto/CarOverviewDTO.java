package com.metropolitan.rent_a_car_system.dto;

import com.metropolitan.rent_a_car_system.utils.DataFormatter;

import java.util.UUID;

public class CarOverviewDTO {
    public UUID id;
    public String brand;
    public String model;
    public String engineType;
    public String category;
    public String pricePerDay;

    public CarOverviewDTO(UUID id, String brand, String model, String engineType, String category, double pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
        this.category = category;
        this.pricePerDay = DataFormatter.formatPrice(pricePerDay);
    }

}
