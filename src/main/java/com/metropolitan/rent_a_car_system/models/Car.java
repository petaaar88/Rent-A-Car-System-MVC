package com.metropolitan.rent_a_car_system.models;

import com.metropolitan.rent_a_car_system.enums.EngineType;

import java.util.List;
import java.util.UUID;

public class Car {
    private UUID id;
    private CarBrand brand;
    private CarCategory category;
    private String model;
    private String registrationNumber;
    private String color;
    private int year;
    private EngineType engineType;
    private int horsePower;
    private double pricePerDay;
    private boolean isAvailable;
    private double mileage;
    private List<Reservation> reservations;

    public Car() {}

    public Car(UUID id, CarBrand brand, CarCategory category, String model, String registrationNumber, String color, int year, EngineType engineType, int horsePower, double pricePerDay, boolean isAvailable, double mileage, List<Reservation> reservations) {
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
        this.reservations = reservations;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public CarCategory getCategory() {
        return category;
    }

    public void setCategory(CarCategory category) {
        this.category = category;
    }
}
