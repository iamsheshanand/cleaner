package com.cleaner.repository;

import com.cleaner.entity.CustomerBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersBookingsRepository extends JpaRepository<CustomerBooking, Long> {
    Optional<CustomerBooking> findByBookingNumber(Integer bookingNumber);
}
