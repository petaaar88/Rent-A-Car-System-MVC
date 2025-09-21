package com.metropolitan.rent_a_car_system.utils;

import java.util.regex.Pattern;

public class CredentialsValidator {

    public static boolean validateCredentials(String username, String password) {
        return username.trim().length() > 1 && password.length() > 0;
    }

    public static boolean validateRegistration(String username, String password, String firstName, String lastName, String email, String phone, String address) {
        return username.trim().length() > 1 &&
                password.trim().length() > 0 &&
                firstName.trim().length() > 1 &&
                lastName.trim().length() > 1 &&
                validateEmail(email) &&
                validatePhone(phone) &&
                address.trim().length() > 0;
    }

    public static boolean validateEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email.trim());
    }

    public static boolean validatePhone(String phone) {
        if (phone == null) return false;
        // Primer: dozvoljava +, brojeve i razmake, min 6 cifara
        String phoneRegex = "^\\+?[0-9 ]{6,15}$";
        return Pattern.matches(phoneRegex, phone.trim());
    }
}
