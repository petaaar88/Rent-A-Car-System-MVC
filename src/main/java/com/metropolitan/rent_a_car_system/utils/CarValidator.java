package com.metropolitan.rent_a_car_system.utils;

import com.metropolitan.rent_a_car_system.enums.EngineType;
import com.metropolitan.rent_a_car_system.exceptions.CreateCarException;

public class CarValidator {

    private CarValidator() {}

    public static void validate(
            String model,
            String brand,
            String category,
            String color,
            String registrationNumber,
            EngineType engineType,
            double pricePerDay,
            int year,
            double mileage,
            int horsePower
    ) throws CreateCarException {
        if (model == null || model.trim().isEmpty())
            throw new CreateCarException("Model is required");
        if (brand == null || brand.trim().isEmpty())
            throw new CreateCarException("Brand is required");
        if (category == null || category.trim().isEmpty())
            throw new CreateCarException("Category is required");
        if (color == null || color.trim().isEmpty())
            throw new CreateCarException("Color is required");
        if (registrationNumber == null || registrationNumber.trim().isEmpty())
            throw new CreateCarException("Registration number is required");

        if (engineType == null)
            throw new CreateCarException("Engine type is required");
        if (pricePerDay <= 0)
            throw new CreateCarException("Price per day must be greater than 0");
        if (year <= 0)
            throw new CreateCarException("Year must be greater than 0");
        if (mileage < 0)
            throw new CreateCarException("Mileage cannot be negative");
        if (horsePower <= 0)
            throw new CreateCarException("Horse power must be greater than 0");
    }
}
