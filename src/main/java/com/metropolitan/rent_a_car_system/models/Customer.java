package com.metropolitan.rent_a_car_system.models;

import com.metropolitan.rent_a_car_system.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    private UUID id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String address;
    private List<Reservation> reservations;


    public Customer() {}

    public Customer(UUID id, String fullName, String email, String username, String password, String phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.reservations = new ArrayList<Reservation>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO(this.id, this.fullName, this.email, this.username, this.phone, this.address, this.reservations.size());
    }
}
