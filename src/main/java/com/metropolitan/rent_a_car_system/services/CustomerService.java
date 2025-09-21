package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Moderator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final Database db;

    public CustomerService(Database db) {
        this.db = db;
    }

    @PostConstruct
    public void loadCustomers() {
        Customer customer1 = new Customer(UUID.randomUUID(), "Petar Petrović", "petar@example.com", "petar123", "password", "0611234567", "Beograd");
        Customer customer2 = new Customer(UUID.randomUUID(), "Ana Jovanović", "ana@example.com", "ana123", "password", "0627654321", "Novi Sad");
        Customer customer3 = new Customer(UUID.randomUUID(), "Marko Marković", "marko@example.com", "marko123", "password", "0639876543", "Niš");

        db.setCustomers(new ArrayList<>(List.of(customer1, customer2, customer3)));
    }

    public Optional<Customer> login(String username, String password) {
        return db.getCustomers().stream()
                .filter(k -> k.getUsername().equals(username) &&
                        k.getPassword().equals(password))
                .findFirst();
    }

    public List<Customer> getCustomers() {
        return db.getCustomers();
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return db.getCustomers().stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    public void addCustomer(Customer customer) {
        db.getCustomers().add(customer);
    }

    public boolean customerExists(String username) {
        return db.getCustomers().stream().anyMatch(customer -> customer.getUsername().equalsIgnoreCase(username));
    }


}
