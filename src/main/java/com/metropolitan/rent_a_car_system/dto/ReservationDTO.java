package com.metropolitan.rent_a_car_system.dto;

import java.util.Date;
import java.util.UUID;

public class ReservationDTO {
    public UUID id;
    public UUID carId;
    public String customerFullName;
    public String customerPhoneNumber   ;
    public double totalPrice;
    public double pricePerDay;
    public String registrationNumber;
    public String brand;
    public String model;
    public Date startDate;
    public Date endDate;

    public ReservationDTO() {}

    public ReservationDTO(UUID id, UUID carId, String customerFullName, String customerPhoneNumber, double totalPrice, double pricePerDay, String registrationNumber, String brand, String model, Date startDate, Date endDate) {
        this.id = id;
        this.carId = carId;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.totalPrice = totalPrice;
        this.pricePerDay = pricePerDay;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
