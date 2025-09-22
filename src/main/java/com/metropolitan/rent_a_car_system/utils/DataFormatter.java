package com.metropolitan.rent_a_car_system.utils;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component("dataFormatUtils")
public class DataFormatter {
    public static String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00$");
        return df.format(price);
    }
}
