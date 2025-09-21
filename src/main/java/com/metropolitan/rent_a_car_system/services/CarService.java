package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.dto.CarOverview;
import com.metropolitan.rent_a_car_system.enums.EngineType;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.CarBrand;
import com.metropolitan.rent_a_car_system.models.CarCategory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final Database db;

    public CarService(Database db) {
        this.db = db;
    }

    @PostConstruct
    public void loadCars() {

        CarBrand toyota = db.getCarBrands().get(0);
        CarBrand ford = db.getCarBrands().get(1);

        CarCategory sedan = db.getCarCategories().get(0);
        CarCategory suv = db.getCarCategories().get(1);

        Car car1 = new Car(UUID.randomUUID(), toyota, sedan, "Corolla", "BG123AB", "Crvena", 2020, EngineType.PETROL, 132, 25.0, true, 15000, new ArrayList<>());
        Car car2 = new Car(UUID.randomUUID(), ford, suv, "Explorer", "NS456CD", "Plava", 2019, EngineType.DIESEL, 200, 40.0, true, 30000, new ArrayList<>());
        Car car3 = new Car(UUID.randomUUID(), toyota, suv, "RAV4", "NI789EF", "Bela", 2021, EngineType.HYBRID, 180, 35.0, true, 20000, new ArrayList<>());

        db.setCars(new ArrayList<>(List.of(car1, car2, car3)));
    }

    public List<CarOverview> search(String search, String brand, String category, String engineType) {
        return db.getCars().stream()
                .filter(car -> {
                    boolean matches = true;

                    if (search != null && !search.isEmpty()) {
                        String lowerSearch = search.toLowerCase();
                        matches = matches && (
                                car.getModel().toLowerCase().contains(lowerSearch) ||
                                        car.getBrand().getName().toLowerCase().contains(lowerSearch) ||
                                        car.getCategory().getName().toLowerCase().contains(lowerSearch) ||
                                        car.getEngineType().name().toLowerCase().contains(lowerSearch)
                        );
                    }

                    if (brand != null && !brand.isEmpty()) {
                        matches = matches && car.getBrand().getName().equalsIgnoreCase(brand);
                    }

                    if (category != null && !category.isEmpty()) {
                        matches = matches && car.getCategory().getName().equalsIgnoreCase(category);
                    }

                    if (engineType != null && !engineType.isEmpty()) {
                        matches = matches && car.getEngineType().name().equalsIgnoreCase(engineType);
                    }

                    return matches;
                })
                .map( car -> car.toCarOverview()).toList();
    }

    public Car getCar(UUID id) {
        return db.getCars().stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);
    }

}
