package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.RegisterException;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CustomerService;
import com.metropolitan.rent_a_car_system.services.RegisterService;
import com.metropolitan.rent_a_car_system.utils.CredentialsValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RegisterController {

    private final RegisterService registerService;
    private final SessionUser sessionUser;

    public RegisterController(RegisterService registerService, SessionUser sessionUser) {
        this.registerService = registerService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        if(sessionUser.isLoggedIn()) return "redirect:/cars";

        model.addAttribute("error", false);
        model.addAttribute("errorMessage", false);

        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String email,
                            @RequestParam String phone,
                            @RequestParam String address,
                            Model model) {


        try{
            registerService.register(username, password, firstName, lastName, email, phone, address, sessionUser);
        }
        catch (RegisterException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }

        return "redirect:/cars";

    }



}
