package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.dto.AllReservationsDTO;
import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.ReservationException;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/reservations")
    public String getReservations(Model model){
        if (!sessionUser.isLoggedIn())
            return "redirect:/login";

        if(sessionUser.getRole().equals(UserRole.ADMIN))
            return "redirect:/cars";

        model.addAttribute("isAdmin",false);
        AllReservationsDTO allReservationsDTO = reservationService.getAllCustomerReservations(sessionUser.getUserId());
        model.addAttribute("currentReservations", allReservationsDTO.current);
        model.addAttribute("pastReservations", allReservationsDTO.past);


        return "reservations";
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

    @PostMapping("/reservations/{id}/delete")
    public String delete(@PathVariable("id") UUID id) {
        if (!sessionUser.isLoggedIn())
            return "redirect:/login";

        if (sessionUser.getRole().equals(UserRole.ADMIN))
            return "redirect:/cars";

        reservationService.deleteReservation(id);

        return "redirect:/reservations";
    }
}
