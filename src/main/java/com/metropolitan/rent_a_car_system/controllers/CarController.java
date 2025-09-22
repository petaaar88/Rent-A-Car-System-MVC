package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.dto.CarDetailsDTO;
import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CarBrandService;
import com.metropolitan.rent_a_car_system.services.CarCategoryService;
import com.metropolitan.rent_a_car_system.services.CarService;
import com.metropolitan.rent_a_car_system.utils.DataFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class CarController {

    private final CarBrandService carBrandService;
    private final CarCategoryService carCategoryService;
    private final CarService carService;
    private final SessionUser sessionUser;
    private final DataFormatter dataFormatUtils;



    public CarController(CarBrandService carBrandService, CarCategoryService carCategoryService, CarService carService, SessionUser sessionUser, DataFormatter dataFormatUtils) {
        this.carBrandService = carBrandService;
        this.carCategoryService = carCategoryService;
        this.carService = carService;
        this.sessionUser = sessionUser;
        this.dataFormatUtils = dataFormatUtils;
    }

    @GetMapping("/cars")
    public String getCars(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String engineType,
            Model model) {
        if (!sessionUser.isLoggedIn()) return "redirect:/login";

        model.addAttribute("cars", carService.search(search, brand, category, engineType));
        model.addAttribute("brands", carBrandService.getCarBrands());
        model.addAttribute("categories", carCategoryService.getCarCategories());

        return "cars";
    }

    @GetMapping("/cars/{id}")
    public String getCar(@PathVariable("id")UUID id, Model model) {
        if (!sessionUser.isLoggedIn()) return "redirect:/login";
        CarDetailsDTO car = carService.getCar(id);
        if (car == null)
            return "redirect:/cars";

        model.addAttribute("car", car);
        model.addAttribute("unavailable",!car.isAvailable);
        model.addAttribute("isAdmin",sessionUser.getRole().equals(UserRole.ADMIN));
        model.addAttribute("dataFormatUtils", dataFormatUtils);

        return "car-details";
    }
}
