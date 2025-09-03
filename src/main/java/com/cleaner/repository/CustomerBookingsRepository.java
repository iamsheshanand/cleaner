package com.cleaner.repository;

import com.cleaner.records.CustomerBooking;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerBookingsRepository {
    private static final String DATE_2026_01_10 = "2026-01-10";
    private static final String DATE_2025_10_01 = "2025-10-01";


    public List<CustomerBooking> findAllCustomerBookings() {
        CustomerBooking customerBookingNumber1 = new CustomerBooking(1, 4, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber2 = new CustomerBooking(2, 2, LocalDate.parse(DATE_2026_01_10));
        CustomerBooking customerBookingNumber3 = new CustomerBooking(3, 1, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber4 = new CustomerBooking(4, 3, LocalDate.parse(DATE_2025_10_01));
        return List.of(customerBookingNumber1, customerBookingNumber2, customerBookingNumber3, customerBookingNumber4);
    }

    public Optional<CustomerBooking> findCustomerBookingByBookingId(Integer bookingNumber) {
        return findAllCustomerBookings().stream()
                .filter(customerBooking ->
                        customerBooking.bookingNumber().equals(bookingNumber))
                .findFirst();
    }

    public List<CustomerBooking> findCustomersBookingBySpecifiedDate(LocalDate specifiedDate) {
        //multiple bookings for a specified date
        return findAllCustomerBookings().stream()
                .filter(customerBooking ->
                        customerBooking.date().equals(specifiedDate))
                .toList();
    }

}
