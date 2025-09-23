package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.dto.CustomerDTO;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Moderator;
import com.metropolitan.rent_a_car_system.models.Reservation;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

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

        List<Car> cars = db.getCars();
        if (cars.size() < 6) return;

        Date start1 = toDate(2025, 5, 25);
        Date end1 = toDate(2025, 5, 28);

        Date start2 = toDate(2023, 10, 1);
        Date end2 = toDate(2023, 10, 5);

        Date start3 = toDate(2025, 2, 10);
        Date end3 = toDate(2025, 3, 1);

        Reservation r1 = new Reservation(UUID.randomUUID(), cars.get(0), customer1, start1, end1, cars.get(0).getPricePerDay() * daysBetween(start1, end1));
        Reservation r2 = new Reservation(UUID.randomUUID(), cars.get(1), customer2, start2, end2, cars.get(1).getPricePerDay() * daysBetween(start2, end2));
        Reservation r3 = new Reservation(UUID.randomUUID(), cars.get(2), customer3, start3, end3, cars.get(2).getPricePerDay() * daysBetween(start3, end3));

        r1.getCar().getReservations().add(r1);
        r1.getCustomer().getReservations().add(r1);

        r2.getCar().getReservations().add(r2);
        r2.getCustomer().getReservations().add(r2);

        r3.getCar().getReservations().add(r3);
        r3.getCustomer().getReservations().add(r3);
    }

    private Date toDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private long daysBetween(Date start, Date end) {
        return (end.getTime() - start.getTime()) / (24L * 60 * 60 * 1000);
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

    public CustomerDTO getCustomer(UUID id) {
        Optional<Customer> optionalCustomer = getCustomerById(id);
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get().toCustomerDTO();
        }
        return null;
    }


}
