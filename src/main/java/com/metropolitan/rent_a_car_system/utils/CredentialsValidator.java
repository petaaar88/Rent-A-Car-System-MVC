package com.metropolitan.rent_a_car_system.utils;

import com.metropolitan.rent_a_car_system.exceptions.CredentialsValidationException;

import java.util.regex.Pattern;

public class CredentialsValidator {

    public static boolean validateCredentials(String username, String password) {
        return username.trim().length() > 1 && password.length() > 0;
    }

    public static boolean validateRegistration(String username, String password, String firstName, String lastName, String email, String phone, String address)throws CredentialsValidationException {
        return username.trim().length() > 1 &&
                password.trim().length() > 0 &&
                firstName.trim().length() > 1 &&
                lastName.trim().length() > 1 &&
                validateEmail(email) &&
                validatePhone(phone) &&
                address.trim().length() > 0;
    }

    public static boolean validateEmail(String email) throws CredentialsValidationException {
        if (email == null) return false;
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";

        if(!Pattern.matches(emailRegex, email.trim()))
            throw new CredentialsValidationException("Invalid email.");

        return true;
    }

    public static boolean validatePhone(String phone) throws CredentialsValidationException{
        if (phone == null) return false;

        String phoneRegex = "^\\+?[0-9 ]{6,15}$";

        if(!Pattern.matches(phoneRegex, phone.trim()))
            throw new CredentialsValidationException("Invalid phone.");

        return true;
    }
}
