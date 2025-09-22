package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.LoginException;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Moderator;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CustomerService;
import com.metropolitan.rent_a_car_system.services.LoginService;
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
    private final LoginService loginService;


    public LoginController(SessionUser sessionUser,LoginService loginService) {
        this.sessionUser = sessionUser;
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String form(Model model) {
        if(sessionUser.isLoggedIn()) return "redirect:/cars";

        model.addAttribute("error", false);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {


        try{
            loginService.login(username, password, sessionUser);
        }
        catch(LoginException e){
            model.addAttribute("error", true);
            return "login";
        }

        return "redirect:/cars";
    }

    @GetMapping("/logout")
    public String logout() {
        sessionUser.logout();
        return "redirect:/login";
    }



}
