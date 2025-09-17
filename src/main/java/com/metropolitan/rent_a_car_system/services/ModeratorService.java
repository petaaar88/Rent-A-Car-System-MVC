package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.db.Database;
import com.metropolitan.rent_a_car_system.models.Moderator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModeratorService {

    private final Database db;

    public ModeratorService(Database db) {
        this.db = db;
    }

    @PostConstruct
    public void loadModerators() {
        db.setModerators(List.of(new Moderator(UUID.randomUUID(), "admin", "admin")));
    }

    public Optional<Moderator> login(String username, String password) {
        return db.getModerators().stream()
                .filter(k -> k.getUsername().equals(username) &&
                        k.getPassword().equals(password))
                .findFirst();
    }

}
