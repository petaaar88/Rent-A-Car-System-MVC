package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.dto.AllReservationsDTO;
import com.metropolitan.rent_a_car_system.dto.CarDetailsDTO;
import com.metropolitan.rent_a_car_system.enums.EngineType;
import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.CreateCarException;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CarBrandService;
import com.metropolitan.rent_a_car_system.services.CarCategoryService;
import com.metropolitan.rent_a_car_system.services.CarService;
import com.metropolitan.rent_a_car_system.utils.DataFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/create-car")
    public String createCar(Model model) {
        if (!sessionUser.isLoggedIn()) return "redirect:/login";
        if (!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        model.addAttribute("brands", carBrandService.getCarBrands());
        model.addAttribute("categories", carCategoryService.getCarCategories());
        model.addAttribute("isAdmin",sessionUser.getRole().equals(UserRole.ADMIN));
        model.addAttribute("error",false);

        return "create-car";
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
        model.addAttribute("isAdmin",sessionUser.getRole().equals(UserRole.ADMIN));

        return "cars";
    }

    @PostMapping("/cars")
    public String createCar( @RequestParam String model,
                             @RequestParam String brand,
                             @RequestParam String category,
                             @RequestParam String engineType,
                             @RequestParam double pricePerDay,
                             @RequestParam String color,
                             @RequestParam int year,
                             @RequestParam String registrationNumber,
                             @RequestParam double milage,
                             @RequestParam int horsePower,
                             Model modelUI) {

        if (!sessionUser.isLoggedIn()) return "redirect:/login";
        if (!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        try{
            carService.createCar(model, brand, category, engineType, pricePerDay, color, year, registrationNumber, milage, horsePower);

        } catch (CreateCarException e) {
            modelUI.addAttribute("error", true);
            modelUI.addAttribute("errorMessage", e.getMessage());
            modelUI.addAttribute("isAdmin", sessionUser.getRole().equals(UserRole.ADMIN));

            return "create-car";
        }
        return "redirect:/cars";
    }

    @PostMapping("/cars/{id}/update")
    public String updateCar(@PathVariable("id")UUID id,
                            @RequestParam String model,
                            @RequestParam String brand,
                            @RequestParam String category,
                            @RequestParam String engineType,
                            @RequestParam double pricePerDay,
                            @RequestParam String color,
                            @RequestParam int year,
                            @RequestParam String registrationNumber,
                            @RequestParam double milage,
                            @RequestParam int horsePower,
                            Model modelUI,
                            RedirectAttributes redirectAttributes) {
        if (!sessionUser.isLoggedIn()) return "redirect:/login";
        if (!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        try{
            carService.updateCar(id, model, brand, category, engineType, pricePerDay, color, year, registrationNumber, milage, horsePower);
        }
        catch (CreateCarException e){
            redirectAttributes.addFlashAttribute("updateError", true);
            redirectAttributes.addFlashAttribute("updateErrorMessage", e.getMessage());
        }

        return "redirect:/cars/" + id;
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
        model.addAttribute("isAdmin",sessionUser.getRole().equals(UserRole.ADMIN));

        if(sessionUser.getRole().equals(UserRole.ADMIN)) {
            AllReservationsDTO allReservations = carService.getCarReservations(id);
            model.addAttribute("currentReservations", allReservations.current);
            model.addAttribute("pastReservations", allReservations.past);
            model.addAttribute("brands", carBrandService.getCarBrands());
            model.addAttribute("categories", carCategoryService.getCarCategories());
        }

        return "car-details";
    }
}
