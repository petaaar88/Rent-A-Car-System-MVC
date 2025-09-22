package com.metropolitan.rent_a_car_system.models;

import com.metropolitan.rent_a_car_system.dto.ReservationDTO;

import java.util.Date;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private Car car;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalPrice;

    public Reservation() {}

    public Reservation(UUID id, Car car, Customer customer, Date startDate, Date endDate, double totalPrice) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
