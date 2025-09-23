package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.BrandException;
import com.metropolitan.rent_a_car_system.models.CarBrand;
import com.metropolitan.rent_a_car_system.models.CarCategory;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CarBrandService;
import com.metropolitan.rent_a_car_system.services.CarCategoryService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class CarBrandController {

    private final CarBrandService carBrandService;
    private final SessionUser sessionUser;

    public CarBrandController(CarBrandService carBrandService, SessionUser sessionUser) {
        this.carBrandService = carBrandService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/brands")
    public String getBrands(Model model) {

        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        List<CarBrand> carBrands = carBrandService.getCarBrands();
        model.addAttribute("brands", carBrands);
        model.addAttribute("deleteError",false);
        model.addAttribute("createError",false);
        model.addAttribute("isAdmin",true);

        return "brands";
    }

    @PostMapping("/brands/{id}/delete")
    public String deleteCarBrand(@PathVariable("id") UUID id, Model model) {


        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";


        if(!carBrandService.deleteCarBrand(id))
            model.addAttribute("deleteError",true);

        model.addAttribute("brands", carBrandService.getCarBrands());
        model.addAttribute("createError", false);
        model.addAttribute("isAdmin", sessionUser.getRole().equals(UserRole.ADMIN));

        return "brands";
    }

    @PostMapping("/brands")
    public String addCarBrand(@RequestParam String name,
                              @RequestParam String description,
                              Model model) {

        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        try{
            carBrandService.addCarBrand(name,description);
        }
        catch(BrandException e){
            model.addAttribute("brands", carBrandService.getCarBrands());
            model.addAttribute("createError", true);
            model.addAttribute("createErrorMessage", e.getMessage());
            model.addAttribute("isAdmin", sessionUser.getRole().equals(UserRole.ADMIN));
            model.addAttribute("deleteError",false);

            return "brands";
        }
        return "redirect:/brands";
    }
}
