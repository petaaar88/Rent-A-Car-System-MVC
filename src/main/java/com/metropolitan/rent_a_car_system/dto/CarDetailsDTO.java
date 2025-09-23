package com.metropolitan.rent_a_car_system.dto;

import java.util.UUID;

public class CarDetailsDTO {
    public UUID id;
    public String brand;
    public String category;
    public String model;
    public String registrationNumber;
    public String color;
    public int year;
    public String engineType;
    public int horsePower;
    public double pricePerDay;
    public boolean isAvailable;
    public double mileage;
    public boolean isVisible;

    public CarDetailsDTO() {}

    public CarDetailsDTO(UUID id, String brand, String category, String model, String registrationNumber, String color, int year, String engineType, int horsePower, double pricePerDay, boolean isAvailable, double mileage) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.year = year;
        this.engineType = engineType;
        this.horsePower = horsePower;

        this.pricePerDay = pricePerDay;
        this.isAvailable = isAvailable;
        this.mileage = mileage;
        this.isVisible = true;
    }

    public CarDetailsDTO(UUID id, String brand, String category, String model, String registrationNumber, String color, int year, String engineType, int horsePower, double pricePerDay, boolean isAvailable, double mileage, boolean isVisible) {
        this.id = id;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.color = color;
        this.year = year;
        this.engineType = engineType;
        this.horsePower = horsePower;

        this.pricePerDay = pricePerDay;
        this.isAvailable = isAvailable;
        this.mileage = mileage;
        this.isVisible = isVisible;
    }


}
