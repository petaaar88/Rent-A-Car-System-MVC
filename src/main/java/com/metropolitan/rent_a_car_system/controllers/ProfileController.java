package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    private final SessionUser sessionUser;
    private final CustomerService customerService;

    public ProfileController(SessionUser sessionUser, CustomerService customerService) {
        this.sessionUser = sessionUser;
        this.customerService = customerService;
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        model.addAttribute("customer", customerService.getCustomer(sessionUser.getUserId()));
        model.addAttribute("isAdmin", false);
        return "profile";
    }
}
