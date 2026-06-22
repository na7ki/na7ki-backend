package com.na7ki.backend.common.util;

import java.time.LocalDate;
import java.time.Period;

public final class DateUtils {

    private DateUtils() {
        // makes the class static. Prevents instantiation
    }

    public static byte calculateAge(LocalDate dateOfBirth) {
        return (byte) Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
