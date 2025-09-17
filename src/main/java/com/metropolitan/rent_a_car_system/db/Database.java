package com.metropolitan.rent_a_car_system.db;

import com.metropolitan.rent_a_car_system.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

@Component
@ApplicationScope
public class Database {
    List<Car> cars = new ArrayList<>();
    List<CarBrand> carBrands = new ArrayList<>();
    List<CarCategory> carCategories = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    List<Moderator> moderators = new ArrayList<>();

    public Database() {}

    @Autowired
    public Database(List<Car> cars, List<CarBrand> carBrands, List<CarCategory> carCategories, List<Customer> customers, List<Moderator> moderators) {
        this.cars = cars;
        this.carBrands = carBrands;
        this.carCategories = carCategories;
        this.customers = customers;
        this.moderators = moderators;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<CarBrand> getCarBrands() {
        return carBrands;
    }

    public void setCarBrands(List<CarBrand> carBrands) {
        this.carBrands = carBrands;
    }

    public List<CarCategory> getCarCategories() {
        return carCategories;
    }

    public void setCarCategories(List<CarCategory> carCategories) {
        this.carCategories = carCategories;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Moderator> getModerators() {
        return moderators;
    }

    public void setModerators(List<Moderator> moderators) {
        this.moderators = moderators;
    }

}
