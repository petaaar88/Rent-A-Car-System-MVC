package com.metropolitan.rent_a_car_system.dto;


import java.util.UUID;

public class CustomerDTO {
    public UUID id;
    public String fullName;
    public String email;
    public String username;
    public String phone;
    public String address;
    public int reservationsNumber;

    public CustomerDTO(UUID id, String fullName, String email, String username, String phone, String address, int reservationsNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.reservationsNumber = reservationsNumber;
    }
}
