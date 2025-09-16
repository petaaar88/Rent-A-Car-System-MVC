package com.metropolitan.rent_a_car_system.models;

import java.util.Date;
import java.util.UUID;

public class Reservation {
    private UUID id;
    private Car car;
    private Customer customer;
    private Date startDate;
    private Date endDate;
    private double totalPrice;
}
