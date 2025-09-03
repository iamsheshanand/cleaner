package com.cleaner.controller;

import com.cleaner.service.interfaces.CleaningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/cleaning")
@Slf4j
public class WindowCleaningController {

    private final CleaningService windowsCleaningService;

    @PostConstruct
    public void init() {
        log.info("WindowCleaningController initiated");
    }

    //@Qualifier("windowCleaningService")
    public WindowCleaningController(CleaningService windowsCleaningService) {
        this.windowsCleaningService = windowsCleaningService;
    }

    @Operation(summary = "${cleaning.cost.summary}",
            description = "${clean.cost.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${cleaning.cost.success.response.description}"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request")

    })
    @GetMapping("/booking/{bookingNumber}/cost/total")
    public ResponseEntity<Integer> cleaningCostTotalPerBooking(
            @PathVariable(value = "bookingNumber")
            @Valid Integer bookingNumber) {
        log.info("Booking number: {}", bookingNumber);
        Integer cleaningCostTotal = windowsCleaningService
                .calculateTotalCostForSpecifiedBookingNumber(bookingNumber);
        log.info("Cleaning cost total: {}", cleaningCostTotal);
        return ResponseEntity.ok(cleaningCostTotal);
    }

    @Operation(summary = "${total.windows.fordate.summary}",
            description = "${total.windows.fordate.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "${total.windows.for.date.success.response.deescription}"),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error"),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request")
    })
    @GetMapping("/windows/{specifiedDate}/total")
    public ResponseEntity<Integer> totalWindowsCleanedForSpecifiedDate(
            @PathVariable(value = "specifiedDate")
            @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate specifiedDate) {
        log.info("Specified date: {}", specifiedDate);
        Integer totalNumberOfWindowsCleanedForSpecifiedDate = windowsCleaningService
                .calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(specifiedDate);
        log.info("{} windows cleaned for specified date {}",
                totalNumberOfWindowsCleanedForSpecifiedDate, specifiedDate
        );
        return ResponseEntity.ok(totalNumberOfWindowsCleanedForSpecifiedDate);
    }
}
