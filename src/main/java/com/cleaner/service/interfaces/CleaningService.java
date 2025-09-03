package com.cleaner.service.interfaces;

import java.time.LocalDate;

public interface CleaningService {
    Integer calculateTotalCostForSpecifiedBookingNumber(Integer bookingNumber);
    Integer calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(LocalDate specifiedDate);
}
