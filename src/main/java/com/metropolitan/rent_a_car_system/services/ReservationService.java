package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.exceptions.ReservationException;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ReservationService {

    private final Database db;

    public ReservationService(Database db) {
        this.db = db;
    }

    public void createReservation(UUID carId, UUID customerId, Date startDate, Date endDate)throws ReservationException {
        Car car = db.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new ReservationException("Car not found"));

        Customer customer = db.getCustomers().stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new ReservationException("Customer not found"));

        boolean conflict = car.getReservations().stream()
                .anyMatch(r -> !(endDate.before(r.getStartDate()) || startDate.after(r.getEndDate())));
        if (conflict) throw new ReservationException("Car is already reserved for the selected period");

        long diffMillis = endDate.getTime() - startDate.getTime();
        int days = (int) (diffMillis / (1000 * 60 * 60 * 24)) + 1;
        double totalPrice = days * car.getPricePerDay();

        if(!car.isAvailable()) throw new ReservationException("Car is not available");

        Reservation reservation = new Reservation(UUID.randomUUID(), car, customer, startDate, endDate, totalPrice);
        car.getReservations().add(reservation);
        car.setAvailable(false);
        customer.getReservations().add(reservation);
    }
}
