package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.services.CarBrandService;
import com.metropolitan.rent_a_car_system.services.CarCategoryService;
import com.metropolitan.rent_a_car_system.services.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarController {

    private final CarBrandService carBrandService;
    private final CarCategoryService carCategoryService;
    private final CarService carService;


    public CarController(CarBrandService carBrandService, CarCategoryService carCategoryService, CarService carService) {
        this.carBrandService = carBrandService;
        this.carCategoryService = carCategoryService;
        this.carService = carService;
    }

    @GetMapping("/cars")
    public String getCars(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String engineType,
            Model model) {

        model.addAttribute("cars", carService.search(search, brand, category, engineType));
        model.addAttribute("brands", carBrandService.getCarBrands());
        model.addAttribute("categories", carCategoryService.getCarCategories());

        return "cars";
    }
}
