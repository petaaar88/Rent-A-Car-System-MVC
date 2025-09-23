package com.metropolitan.rent_a_car_system.controllers;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.BrandException;
import com.metropolitan.rent_a_car_system.exceptions.CategoryException;
import com.metropolitan.rent_a_car_system.models.CarCategory;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.services.CarCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class CarCategoryController {
    private final CarCategoryService carCategoryService;
    private final SessionUser sessionUser;

    public CarCategoryController(CarCategoryService carCategoryService, SessionUser sessionUser) {
        this.carCategoryService = carCategoryService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {

        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        List<CarCategory> carCategories = carCategoryService.getCarCategories();
        model.addAttribute("categories", carCategories);
        model.addAttribute("deleteError",false);
        model.addAttribute("createError",false);
        model.addAttribute("isAdmin",true);

        return "categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCarCategory(@PathVariable("id") UUID id, Model model) {


        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";


        if(!carCategoryService.deleteCarCategory(id))
            model.addAttribute("deleteError",true);

        model.addAttribute("categories", carCategoryService.getCarCategories());
        model.addAttribute("createError", false);
        model.addAttribute("isAdmin", sessionUser.getRole().equals(UserRole.ADMIN));

        return "categories";
    }

    @PostMapping("/categories")
    public String addCarCategory(@RequestParam String name,
                              @RequestParam String description,
                              Model model) {

        if(!sessionUser.isLoggedIn()) return "redirect:/login";
        if(!sessionUser.getRole().equals(UserRole.ADMIN)) return "redirect:/cars";

        try{
            carCategoryService.addCarCategory(name,description);
        }
        catch(CategoryException e){
            model.addAttribute("categories", carCategoryService.getCarCategories());
            model.addAttribute("createError", true);
            model.addAttribute("createErrorMessage", e.getMessage());
            model.addAttribute("isAdmin", sessionUser.getRole().equals(UserRole.ADMIN));
            model.addAttribute("deleteError",false);

            return "categories";
        }
        return "redirect:/categories";
    }
}
