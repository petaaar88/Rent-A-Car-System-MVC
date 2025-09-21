package com.metropolitan.rent_a_car_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CarController {

    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", List.of("car1", "car2", "car3"));
        return "cars";
    }
}
