package com.cleaner.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.NumberFormat;

public record Customer(
        @NotNull(message = "customer number cannot be null")
        @Positive(message = "customer number should be positive")
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        Integer customerNumber,
        @NotBlank(message = "name cannot be blank")
        String name,
        @NotNull
        @Positive
        @NumberFormat(style = NumberFormat.Style.NUMBER)
        Integer windows,
        String loyaltyScheme,
        Integer frequency
        ) {
}
