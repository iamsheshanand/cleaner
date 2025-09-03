package com.cleaner.service;

import com.cleaner.entity.CustomerBooking;
import com.cleaner.repository.CustomersBookingsRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomersBookingsService {
    public static final String DATE_2025_10_01 = "2025-10-01";
    public static final String DATE_2026_01_10 = "2026-01-10";

    private CustomersBookingsRepository customersBookingsRepository;

    public void saveAll(List<CustomerBooking> customerBookings) {
        customersBookingsRepository.saveAll(customerBookings);
    }

    public List<CustomerBooking> findAll() {
        return customersBookingsRepository.findAll();
    }

    public Optional<CustomerBooking> findByCustomerId(long customerId) {
        return customersBookingsRepository.findById(customerId);
    }

    public Optional<CustomerBooking> findByBookingNumber(Integer bookingNumber) {
        return customersBookingsRepository.findByBookingNumber(bookingNumber);
    }

    @PostConstruct
    public void init() {
        log.info("CustomersBookingsService initiated");
    }


}
