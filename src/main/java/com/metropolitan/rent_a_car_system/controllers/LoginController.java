package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Moderator;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CustomerService;
import com.metropolitan.rent_a_car_system.services.ModeratorService;
import com.metropolitan.rent_a_car_system.utils.CredentialsValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final SessionUser sessionUser;
    private final CustomerService customerService;
    private final ModeratorService moderatorService;


    public LoginController(SessionUser sessionUser, CustomerService customerService, ModeratorService moderatorService) {
        this.sessionUser = sessionUser;
        this.customerService = customerService;
        this.moderatorService = moderatorService;
    }

    @GetMapping("/login")
    public String form(Model model) {
        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        if(!CredentialsValidator.validateCredentials(username, password)){
            model.addAttribute("error", true);
            return "login";
        };

        Optional<Moderator> optionalModerator = moderatorService.login(username, password);
        if (optionalModerator.isPresent()) {
            Moderator k = optionalModerator.get();
            sessionUser.setRole(UserRole.ADMIN);
            sessionUser.setUserId(k.getId());
            return "redirect:/cars"; // TODO: uradi drugi redirekt
        }

        Optional<Customer> optionalUser = customerService.login(username, password);
        if (optionalUser.isPresent()) {
            Customer k = optionalUser.get();
            sessionUser.setRole(UserRole.USER);
            sessionUser.setUserId(k.getId());
            return "redirect:/cars"; // TODO: uradi drugi redirekt
        }

        model.addAttribute("error", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        sessionUser.logout();
        return "redirect:/login";
    }



}
