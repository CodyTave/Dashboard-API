package com.dashboard.App;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component

public class Utilities {
    public static String toSlug(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\-]+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }
    public static LocalDate getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = LocalDate.now().format(formatter);
        return LocalDate.parse(formattedDate, formatter);
    }

}
