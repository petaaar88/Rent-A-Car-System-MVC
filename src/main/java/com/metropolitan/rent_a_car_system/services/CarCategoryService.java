package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.exceptions.CategoryException;
import com.metropolitan.rent_a_car_system.models.CarCategory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarCategoryService {
    private final Database db;

    public CarCategoryService(Database db) {
        this.db = db;
    }

    @PostConstruct
    public void loadCarCategories() {
        db.setCarCategories(new ArrayList<>(List.of(
                new CarCategory(UUID.randomUUID(), "Sedan", "A passenger car in a three-box configuration with separate compartments for engine, passenger, and cargo."),
                new CarCategory(UUID.randomUUID(), "SUV", "Sport Utility Vehicle, combines elements of road-going passenger cars with features from off-road vehicles."),
                new CarCategory(UUID.randomUUID(), "Hatchback", "A car with a hatch-type rear door that opens upwards and a shared passenger/cargo space."),
                new CarCategory(UUID.randomUUID(), "Coupe", "A car with a fixed-roof body style, usually with two doors."),
                new CarCategory(UUID.randomUUID(), "Convertible", "A car with a roof structure that can be 'converted' to allow open-air or enclosed driving."),
                new CarCategory(UUID.randomUUID(), "Pickup Truck", "A light-duty truck with an enclosed cab and an open cargo area with low sides and tailgate.")
        )));
    }

    public boolean deleteCarCategory(UUID id) {
        boolean existsCarWithCategory = db.getCars().stream()
                .anyMatch(car -> car.getCategory().getId().equals(id));

        if (existsCarWithCategory)
            return false;

        return db.getCarCategories().removeIf(carCategory -> carCategory.getId().equals(id));
    }

    public void addCarCategory(String name, String description)throws CategoryException {
        if (db.getCarCategories().stream().anyMatch(carCategory -> carCategory.getName().equals(name)))
            throw new CategoryException("Category with name " + name + " already exists.");

        if(name.trim().length() == 0 || description.trim().length() == 0)
            throw new CategoryException("Category name and description cannot be empty.");

        db.getCarCategories().add(new CarCategory(UUID.randomUUID(), name, description));
    }

    public List<CarCategory> getCarCategories() {
        return db.getCarCategories();
    }
}
