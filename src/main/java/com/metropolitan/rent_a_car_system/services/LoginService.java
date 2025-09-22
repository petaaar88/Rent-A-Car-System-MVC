package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.LoginException;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.Moderator;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.utils.CredentialsValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final ModeratorService moderatorService;
    private final CustomerService customerService;

    public LoginService(ModeratorService moderatorService, CustomerService customerService) {
        this.moderatorService = moderatorService;
        this.customerService = customerService;
    }

    public void login(String username, String password, SessionUser sessionUser)throws LoginException {
        if(!CredentialsValidator.validateCredentials(username, password))throw new LoginException("Invalid username or password.");

        Optional<Moderator> optionalModerator = moderatorService.login(username, password);
        if (optionalModerator.isPresent()) {
            Moderator k = optionalModerator.get();
            sessionUser.setRole(UserRole.ADMIN);
            sessionUser.setUserId(k.getId());
            return;
        }

        Optional<Customer> optionalUser = customerService.login(username, password);
        if (optionalUser.isPresent()) {
            Customer k = optionalUser.get();
            sessionUser.setRole(UserRole.USER);
            sessionUser.setUserId(k.getId());
           return;
        }

        throw new LoginException("Invalid username or password.");
    }
}
