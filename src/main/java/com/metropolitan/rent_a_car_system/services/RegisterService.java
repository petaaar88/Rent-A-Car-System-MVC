package com.metropolitan.rent_a_car_system.services;

import com.metropolitan.rent_a_car_system.enums.UserRole;
import com.metropolitan.rent_a_car_system.exceptions.CredentialsValidationException;
import com.metropolitan.rent_a_car_system.exceptions.RegisterException;
import com.metropolitan.rent_a_car_system.models.Customer;
import com.metropolitan.rent_a_car_system.models.SessionUser;
import com.metropolitan.rent_a_car_system.utils.CredentialsValidator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegisterService {

    private final CustomerService customerService;

    public RegisterService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void register(String username, String password, String firstName, String lastName, String email, String phone, String address, SessionUser sessionUser)throws RegisterException {

        try{

        if(!CredentialsValidator.validateRegistration(username, password, firstName, lastName, email, phone, address))
            throw new RegisterException("Invalid data.");
        }
        catch (CredentialsValidationException e){
            throw new RegisterException(e.getMessage());
        }

        if(customerService.customerExists(username))
            throw new RegisterException("User already exists.");

        Customer newCustomer = new Customer(UUID.randomUUID(),(username), email, username, password, phone, address);
        customerService.addCustomer(newCustomer);
        sessionUser.setRole(UserRole.USER);
        sessionUser.setUserId(newCustomer.getId());
    }
}
