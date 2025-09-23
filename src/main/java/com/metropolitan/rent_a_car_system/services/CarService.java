package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.dto.AllReservationsDTO;
import com.metropolitan.rent_a_car_system.dto.CarDetailsDTO;
import com.metropolitan.rent_a_car_system.dto.CarOverviewDTO;
import com.metropolitan.rent_a_car_system.enums.EngineType;
import com.metropolitan.rent_a_car_system.exceptions.CreateCarException;
import com.metropolitan.rent_a_car_system.models.Car;
import com.metropolitan.rent_a_car_system.models.CarBrand;
import com.metropolitan.rent_a_car_system.models.CarCategory;
import com.metropolitan.rent_a_car_system.utils.CarValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final Database db;
    private final ReservationService reservationService;

    public CarService(Database db, ReservationService reservationService) {
        this.reservationService = reservationService;
        this.db = db;
    }

    @PostConstruct
    public void loadCars() {

        CarBrand toyota = db.getCarBrands().get(0);
        CarBrand ford = db.getCarBrands().get(1);
        CarBrand bmw = db.getCarBrands().get(2);
        CarBrand honda = db.getCarBrands().get(3);
        CarBrand mercedes = db.getCarBrands().get(4);
        CarBrand audi = db.getCarBrands().get(5);

        CarCategory sedan = db.getCarCategories().get(0);
        CarCategory suv = db.getCarCategories().get(1);
        CarCategory hatchback = db.getCarCategories().get(2);

        Car car1 = new Car(UUID.randomUUID(), toyota, sedan, "Corolla", "BG123AB", "Crvena", 2020, EngineType.PETROL, 132, 25.0, true, 15000, new ArrayList<>());
        Car car2 = new Car(UUID.randomUUID(), ford, suv, "Explorer", "NS456CD", "Plava", 2019, EngineType.DIESEL, 200, 40.0, true, 30000, new ArrayList<>());
        Car car3 = new Car(UUID.randomUUID(), toyota, suv, "RAV4", "NI789EF", "Bela", 2021, EngineType.HYBRID, 180, 35.0, true, 20000, new ArrayList<>());

        Car car4 = new Car(UUID.randomUUID(), bmw, sedan, "320i", "BG321XY", "Crna", 2022, EngineType.PETROL, 190, 45.0, true, 12000, new ArrayList<>());
        Car car5 = new Car(UUID.randomUUID(), audi, suv, "Q5", "NS654YZ", "Siva", 2021, EngineType.DIESEL, 210, 50.0, true, 25000, new ArrayList<>());
        Car car6 = new Car(UUID.randomUUID(), mercedes, hatchback, "Yaris", "NI987WX", "Plava", 2023, EngineType.HYBRID, 110, 30.0, true, 8000, new ArrayList<>());


        db.setCars(new ArrayList<>(List.of(car1, car2, car3, car4, car5, car6)));
    }

    public void createCar( String model,
                           String brand,
                           String category,
                           String engineType,
                           double pricePerDay,
                           String color,
                           int year,
                           String registrationNumber,
                           double milage,
                           int horsePower)throws CreateCarException {

        CarValidator.validate(model, brand, category,color,registrationNumber,EngineType.valueOf(engineType), pricePerDay, year, milage, horsePower);

        db.getCars().add(new Car(UUID.randomUUID(), db.getCarBrands().stream().filter(carBrand -> carBrand.getName().equals(brand)).findFirst().orElse(null),
                db.getCarCategories().stream().filter(carCategory -> carCategory.getName().equals(category)).findFirst().orElse(null),
                model,
                registrationNumber,
                color,
                year,
                EngineType.valueOf(engineType),
                horsePower,
                milage, true,
                pricePerDay,
                new ArrayList<>()));

    }

    public void updateCar(UUID id, String model, String brand, String category, String engineType, double pricePerDay, String color, int year, String registrationNumber, double milage, int horsePower) throws CreateCarException {
        CarValidator.validate(model, brand, category, color, registrationNumber, EngineType.valueOf(engineType), pricePerDay, year, milage, horsePower);
        db.getCars().stream().filter(car -> car.getId().equals(id)).findFirst().ifPresent(car -> {
            car.setBrand(db.getCarBrands().stream().filter(carBrand -> carBrand.getName().equals(brand)).findFirst().orElse(null));
            car.setCategory(db.getCarCategories().stream().filter(carCategory -> carCategory.getName().equals(category)).findFirst().orElse(null));
            car.setModel(model);
            car.setRegistrationNumber(registrationNumber);
            car.setColor(color);
            car.setYear(year);
            car.setEngineType(EngineType.valueOf(engineType));
            car.setHorsePower(horsePower);
            car.setMileage(milage);
            car.setPricePerDay(pricePerDay);
        });

    }

    public List<CarOverviewDTO> search(String search, String brand, String category, String engineType, boolean isAdmin) {
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

                    if (!isAdmin) {
                        matches = matches && car.isVisible();
                    }

                    return matches;
                })
                .map( car -> car.toCarOverview()).toList();
    }

    public CarDetailsDTO getCar(UUID id) {
        Car car2 =db.getCars().stream().filter(car -> car.getId().equals(id)).findFirst().orElse(null);

        CarDetailsDTO carDetailsDTO = car2 != null ? car2.toCarDetails() : null;
        carDetailsDTO.isVisible = car2.isVisible();

        return carDetailsDTO;
    }

    public void removeFromCatalog(UUID id) {
        db.getCars().stream().filter(car -> car.getId().equals(id)).findFirst().ifPresent(car -> car.setVisible(false));
    }

    public void addToCatalog(UUID id) {
        db.getCars().stream().filter(car -> car.getId().equals(id)).findFirst().ifPresent(car -> car.setVisible(true));
    }

    public AllReservationsDTO getCarReservations(UUID carId) {
        return reservationService.getAllCarReservations(carId);
    }
}
