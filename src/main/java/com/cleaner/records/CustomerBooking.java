package com.cleaner.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public record CustomerBooking(
        @Positive
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        @NotNull(message = "booking number cannot be null")
        Integer bookingNumber,
        @Positive
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        @NotNull(message = "customer number cannot be null")
        Integer customerNumber,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @NotNull(message = "date cannot be null")
        @NotBlank(message = "date cannot be blank")
        LocalDate date) {

}
