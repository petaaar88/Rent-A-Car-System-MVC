package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.exceptions.ReservationException;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.UUID;

@Controller
public class ReservationsController {
    private final SessionUser sessionUser;
    private final ReservationService reservationService;

    public ReservationsController(SessionUser sessionUser, ReservationService reservationService) {
        this.sessionUser = sessionUser;
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public String create(@RequestParam("carId") UUID carId,
                         @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate
                         ) {

        if (!sessionUser.isLoggedIn())
            return "redirect:/login";

        try{
            reservationService.createReservation(carId, sessionUser.getUserId(), startDate, endDate);
        }
        catch (ReservationException e){
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/reservations";
    }

}
