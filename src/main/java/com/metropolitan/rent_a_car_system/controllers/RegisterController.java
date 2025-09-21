package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CustomerService;
import com.metropolitan.rent_a_car_system.utils.CredentialsValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RegisterController {

    private final CustomerService customerService;
    private final SessionUser sessionUser;

    public RegisterController(CustomerService customerService, SessionUser sessionUser) {
        this.customerService = customerService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("exists", false);
        model.addAttribute("invalidData", false);

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

        if(!CredentialsValidator.validateRegistration(username, password, firstName, lastName, email, phone, address)){
            model.addAttribute("invalidData", true);
            return "register";
        }

        if(customerService.customerExists(username)){
            model.addAttribute("exists", true);
            return "register";
        }

        Customer newCustomer = new Customer(UUID.randomUUID(),(username), email, username, password, phone, address);
        customerService.addCustomer(newCustomer);
        sessionUser.setRole(UserRole.USER);
        sessionUser.setUserId(newCustomer.getId());

        return "redirect:/cars"; //TODO: uradi drugi redirect
    }



}
