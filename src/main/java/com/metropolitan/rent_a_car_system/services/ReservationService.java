package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.dto.AllReservationsDTO;
import com.metropolitan.rent_a_car_system.dto.ReservationDTO;
import com.metropolitan.rent_a_car_system.exceptions.ReservationException;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final Database db;

    public ReservationService(Database db) {
        this.db = db;
    }

    public AllReservationsDTO getAllCarReservations(UUID carId) {
        Car car = db.getCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElse(null);

        if (car == null) return new AllReservationsDTO(List.of(), List.of());

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime(); // effectively final

        List<ReservationDTO> current = car.getReservations().stream()
                .filter(r -> !r.getEndDate().before(today))
                .map(this::toDTO)
                .collect(Collectors.toList());

        List<ReservationDTO> past = car.getReservations().stream()
                .filter(r -> r.getEndDate().before(today))
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new AllReservationsDTO(current, past);
    }

    public AllReservationsDTO getAllCustomerReservations(UUID customerId) {
        Customer customer = db.getCustomers().stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst()
                .orElse(null);

        if (customer == null) return new AllReservationsDTO(List.of(), List.of());

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime(); // effectively final

        List<ReservationDTO> current = customer.getReservations().stream()
                .filter(r -> !r.getEndDate().before(today))
                .map(this::toDTO)
                .collect(Collectors.toList());

        List<ReservationDTO> past = customer.getReservations().stream()
                .filter(r -> r.getEndDate().before(today))
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new AllReservationsDTO(current, past);
    }

    private ReservationDTO toDTO(Reservation r) {
        return new ReservationDTO(
                r.getId(),
                r.getCar().getId(),
                r.getCustomer().getFullName(),
                r.getCustomer().getPhone(),
                r.getTotalPrice(),
                r.getCar().getPricePerDay(),
                r.getCar().getRegistrationNumber(),
                r.getCar().getBrand().getName(),
                r.getCar().getModel(),
                r.getStartDate(),
                r.getEndDate()
        );
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

    public void deleteReservation(UUID reservationId) {
        for (Customer customer : db.getCustomers()) {
            customer.getReservations().removeIf(r -> r.getId().equals(reservationId));
        }

        for (Car car : db.getCars()) {
            boolean removed = car.getReservations().removeIf(r -> r.getId().equals(reservationId));
            if (removed) {
                car.setAvailable(true);
            }
        }

    }

}
