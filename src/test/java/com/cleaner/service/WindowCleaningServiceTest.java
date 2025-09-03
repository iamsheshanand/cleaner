package com.cleaner.service;


import com.cleaner.enums.Frequency;
import com.cleaner.enums.LoyaltyScheme;
import com.cleaner.records.Customer;
import com.cleaner.records.CustomerBooking;
import com.cleaner.repository.CustomerBookingsRepository;
import com.cleaner.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WindowCleaningServiceTest {

    private static final String DATE_2025_10_01 = "2025-10-01";
    private static final String DATE_2026_01_10 = "2026-01-10";

    @InjectMocks
    private WindowCleaningService windowCleaningService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerBookingsRepository customerBookingsRepository;

    private List<Customer> customers;
    private List<CustomerBooking> customerBookingsBySpecifiedDate;


    @BeforeEach
    void setUp() {
        CustomerBooking customerBookingNumber1 = new CustomerBooking(1, 4, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber2 = new CustomerBooking(2, 2, LocalDate.parse(DATE_2026_01_10));
        CustomerBooking customerBookingNumber3 = new CustomerBooking(3, 1, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber4 = new CustomerBooking(4, 3, LocalDate.parse(DATE_2025_10_01));

        Customer john = new Customer(1, "John", 10, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);
        Customer paul = new Customer(2, "Paul", 5, LoyaltyScheme.STANDARD, Frequency.NONE);
        Customer ringo = new Customer(3, "Ringo", 12, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);
        Customer george = new Customer(4, "George", 4, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);

        customers = List.of(john, paul, ringo, george);
        customerBookingsBySpecifiedDate = List.of(customerBookingNumber1, customerBookingNumber3, customerBookingNumber4);

    }

    @AfterEach
    void tearDown() {
        customers = null;
        customerBookingsBySpecifiedDate = null;
    }

    @Test
    void calculateTotalQuantityOfWindowsCleanedForASpecifiedDate_DATE_2025_01_10() {
        LocalDate specifiedDate = LocalDate.parse(DATE_2025_10_01);
        Integer expectedTotalQuantity = 26;
        when(customerRepository.findAllCustomers()).thenReturn(customers);
        when(customerBookingsRepository.findCustomersBookingBySpecifiedDate(specifiedDate)).thenReturn(customerBookingsBySpecifiedDate);

        Integer totalQuantityOfWindowsCleanedForASpecifiedDate = windowCleaningService
                .calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(specifiedDate);

        verify(customerRepository, times(1)).findAllCustomers();
        verify(customerBookingsRepository, times(1)).findCustomersBookingBySpecifiedDate(specifiedDate);
        assertEquals(expectedTotalQuantity, totalQuantityOfWindowsCleanedForASpecifiedDate);

    }

    @Test
    void calculatedTotalQuantityOfWindowsCleanedForIncorrectSpecifiedDate_DATE_2026_01_10_shouldFail() {
        LocalDate specifiedDate = LocalDate.parse(DATE_2026_01_10);
        Integer unexpectedTotalQuantity = 26;
        when(customerRepository.findAllCustomers()).thenReturn(customers);
        when(customerBookingsRepository.findCustomersBookingBySpecifiedDate(specifiedDate)).thenReturn(customerBookingsBySpecifiedDate);

        Integer totalQuantityOfWindowsCleanedForASpecifiedDate = windowCleaningService
                .calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(specifiedDate);

        verify(customerRepository, times(1)).findAllCustomers();
        verify(customerBookingsRepository, times(1)).findCustomersBookingBySpecifiedDate(specifiedDate);
        assertNotEquals(unexpectedTotalQuantity, totalQuantityOfWindowsCleanedForASpecifiedDate);


    }

    @Test
    void shouldCalculateTotalCostForSpecifiedBookingNumber_3() {
        Integer specifiedBookingNumber = 3;
        Integer expectedTotalCostForSpecifiedBookingNumber = 15;
        CustomerBooking customerBookingNumber3 = new CustomerBooking(3, 1, LocalDate.parse(DATE_2025_10_01));
        when(customerRepository.findAllCustomers()).thenReturn(customers);
        when(customerBookingsRepository.findCustomerBookingByBookingId(specifiedBookingNumber)).thenReturn(Optional.of(customerBookingNumber3));

        Integer totalCostForSpecifiedBookingNumber = windowCleaningService
                .calculateTotalCostForSpecifiedBookingNumber(specifiedBookingNumber);

        verify(customerRepository, times(1)).findAllCustomers();
        verify(customerBookingsRepository, times(1)).findCustomerBookingByBookingId(specifiedBookingNumber);
        assertEquals(expectedTotalCostForSpecifiedBookingNumber, totalCostForSpecifiedBookingNumber);
    }

}
