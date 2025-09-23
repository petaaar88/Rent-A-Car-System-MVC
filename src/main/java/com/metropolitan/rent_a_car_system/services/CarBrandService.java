package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.exceptions.BrandException;
import com.metropolitan.rent_a_car_system.models.CarBrand;
import com.metropolitan.rent_a_car_system.models.Moderator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarBrandService {

    private final Database db;

    public CarBrandService(Database db) {
        this.db = db;
    }

    @PostConstruct
    public void loadCarBrands() {
        db.setCarBrands(new ArrayList<>(List.of(
                new CarBrand(UUID.randomUUID(), "Toyota", "Japanese car manufacturer known for reliability."),
                new CarBrand(UUID.randomUUID(), "Ford", "American car manufacturer famous for trucks and muscle cars."),
                new CarBrand(UUID.randomUUID(), "BMW", "German luxury car manufacturer."),
                new CarBrand(UUID.randomUUID(), "Honda", "Japanese manufacturer, known for cars and motorcycles."),
                new CarBrand(UUID.randomUUID(), "Mercedes-Benz", "German manufacturer specializing in luxury vehicles."),
                new CarBrand(UUID.randomUUID(), "Audi", "German brand known for technology and performance.")
        )));
    }


    public List<CarBrand> getCarBrands() {
        return db.getCarBrands();
    }

    public void addCarBrand(String name, String description)throws BrandException {
        if (db.getCarBrands().stream().anyMatch(carBrand -> carBrand.getName().equals(name)))
            throw new BrandException("Brand with name " + name + " already exists.");

        if(name.trim().length() == 0 || description.trim().length() == 0)
            throw new BrandException("Brand name and description cannot be empty.");

        db.getCarBrands().add(new CarBrand(UUID.randomUUID(), name, description));
    }

    public CarBrand getCarBrandByName(String name) {
        return db.getCarBrands().stream()
                .filter(carBrand -> carBrand.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteCarBrand(UUID id) {
        boolean existsCarWithBrand = db.getCars().stream()
                .anyMatch(car -> car.getBrand().getId().equals(id));

        if (existsCarWithBrand)
            return false;

        return db.getCarBrands().removeIf(carBrand -> carBrand.getId().equals(id));
    }


    public Optional<CarBrand> getCarBrandById(UUID id) {
        return db.getCarBrands().stream().filter(carBrand -> carBrand.getId().equals(id)).findFirst();
    }

}
