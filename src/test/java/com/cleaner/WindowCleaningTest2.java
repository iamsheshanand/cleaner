package com.cleaner;

import com.cleaner.records.Customer;
import com.cleaner.records.CustomerBooking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WindowCleaningTest2 {

    private static final String DATE_2026_01_10 = "2026-01-10";
    private static final String DATE_2025_10_01 = "2025-10-01";

    private List<Customer> customers;
    private List<CustomerBooking> customerBookings;

    @BeforeEach
    void setUp() {
        CustomerBooking customerBookingNumber1 = new CustomerBooking(1, 4, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber2 = new CustomerBooking(2, 2, LocalDate.parse(DATE_2026_01_10));
        CustomerBooking customerBookingNumber3 = new CustomerBooking(3, 1, LocalDate.parse(DATE_2025_10_01));
        CustomerBooking customerBookingNumber4 = new CustomerBooking(4, 3, LocalDate.parse(DATE_2025_10_01));

        Customer john = new Customer(1, "John", 10, "yes", 4);
        Customer paul = new Customer(2, "Paul", 5, null, 0);
        Customer ringo = new Customer(3, "Ringo", 12, "yes", 2);
        Customer george = new Customer(4, "George", 4, "yes", 4);

        customers = List.of(john, paul, ringo, george);
        customerBookings = List.of(customerBookingNumber1, customerBookingNumber2, customerBookingNumber3, customerBookingNumber4);

    }

    @AfterEach
    void tearDown() {
        customers = null;
        customerBookings = null;
    }

    @Test
    @DisplayName("Should test total quantity of windows cleaned on a specified date 2025-10-01")
    void shouldTestTotalQuantityOfWindowsCleanedOnASpecifiedDate_2025_10_01() {

        Integer totalWindowsCleanedOnSpecifiedDate = 26;

        Map<Integer, Integer> customerWindowsByCustomerNumbers = customers.stream()
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));


        int sum = customerBookings.stream()
                .filter(customerBooking ->
                        customerBooking.date().equals(LocalDate.parse(DATE_2025_10_01)))
                .map(customerBooking ->
                        customerWindowsByCustomerNumbers.get(customerBooking.customerNumber()))
                .mapToInt(Integer::intValue).sum();


        assertEquals(totalWindowsCleanedOnSpecifiedDate, sum);

    }


    @Test
    @DisplayName("Should test total cost for a specified booking number 3")
    void shouldTestTotalCostForASpecifiedBookingNumber3() {
        /*
        Assuming each booking is for one property then £5 for each
        booking and then add the number of windows cleaned
       for that booking
       */
        Integer bookingNumber = 3;
        int costPerPropertyOrBooking = 5;


        Map<Integer, Integer> customerWindowsByCustomerNumbers = customers.stream()
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));

        int numberOfWindowsPerBookingForACustomer = customerBookings.stream()
                .filter(customerBooking ->
                        customerBooking.bookingNumber().equals(bookingNumber))
                .map(customerBooking ->
                        customerWindowsByCustomerNumbers.get(customerBooking.customerNumber()))
                .mapToInt(Integer::intValue).sum();


        /*
         Booking number 3 is with customer number 1
         having 10 windows and has 1 booking on 2025-10-01
         */
        Integer numberOfWindowsForBooking3AndCustomer1 = 10;
        //In this case
        Integer expectedTotalCostPerBooking = numberOfWindowsForBooking3AndCustomer1 + costPerPropertyOrBooking;


        //Based on above assumption
        Integer actualTotalCostForSpecifiedBooking = numberOfWindowsPerBookingForACustomer + costPerPropertyOrBooking;

        assertEquals(expectedTotalCostPerBooking, actualTotalCostForSpecifiedBooking);

    }

    @Test
    @DisplayName("Calculate the total cost for all bookings on a specified date.")
    void shouldCalculateTotalCostForAllBookingsOnASpecifiedDate() {
        Map<Integer, Integer> customerWindowsByCustomerNumbers = customers.stream()
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));
        int totalSumForSpecifedDate = customerBookings.stream()
                .filter(customerBooking -> customerBooking.date()
                        .equals(LocalDate.parse(DATE_2026_01_10)))
                .map(customerBooking -> customerWindowsByCustomerNumbers.get(customerBooking.bookingNumber()))
                .mapToInt(Integer::intValue).sum();

        Integer totalCostPerBookingOnSpecifiedDate = totalSumForSpecifedDate + 5;

        assertEquals(10, totalCostPerBookingOnSpecifiedDate);

    }

    @Test
    @DisplayName("should test pricing policy to include the loyalty scheme discount")
    void shouldTestPricingPolicyToIncludeTheLoyaltySchemeDiscount() {
        int loyaltySchemeDiscountPerProperty = 4;
        double discountPerWindow = 0.75;
        Integer loyalCustomerBookingNumber = 3;

        Map<Integer, Integer> customerWindowsByCustomerNumbers = customers.stream()
                .filter(customer -> customer.loyaltyScheme().equals("yes") && (customer.frequency() == 2 || customer.frequency() == 4))
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));


        int numberOfWindowsPerBookingForACustomer = customerBookings.stream()
                .filter(customerBooking ->
                        customerBooking.bookingNumber().equals(loyalCustomerBookingNumber))
                .map(customerBooking ->
                        customerWindowsByCustomerNumbers.get(customerBooking.customerNumber()))
                .mapToInt(Integer::intValue).sum();

        Double totalCostForLoyalCustomer = loyaltySchemeDiscountPerProperty + numberOfWindowsPerBookingForACustomer * discountPerWindow;

        Double expectedTotalCostForLoyalCustomer = 11.5;

        assertEquals(expectedTotalCostForLoyalCustomer, totalCostForLoyalCustomer);

    }

    @Test
    @DisplayName("should test customer saving by joining the loyalty scheme")
    void shouldTestCustomerSavingByJoinTheLoyaltyScheme() {
        Integer loyalCustomerBookingNumber = 3;
        int loyaltySchemeDiscountPerProperty = 4;
        double discountPerWindow = 0.75;

        Map<Integer, Integer> customerWindowsByCustomerNumbers = customers.stream()
                .filter(customer -> customer.loyaltyScheme().equals("yes") && (customer.frequency() == 2 || customer.frequency() == 4))
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));


        int numberOfWindowsPerBookingForACustomer = customerBookings.stream()
                .filter(customerBooking ->
                        customerBooking.bookingNumber().equals(loyalCustomerBookingNumber))
                .map(customerBooking ->
                        customerWindowsByCustomerNumbers.get(customerBooking.customerNumber()))
                .mapToInt(Integer::intValue).sum();

        Double totalCostForLoyalCustomer = loyaltySchemeDiscountPerProperty + numberOfWindowsPerBookingForACustomer * discountPerWindow;

        Integer actualTotalCostWithoutLoyalty = 5 + numberOfWindowsPerBookingForACustomer;

        Double actualSaving = actualTotalCostWithoutLoyalty - totalCostForLoyalCustomer;
        Double expectedSaving = 3.5;

        assertEquals(expectedSaving, actualSaving);


    }

    @Test
    @DisplayName("should test the mechanism to calculate the next booking date for a loyalty scheme member based on their most recent booking")
    void shouldTestTheMechanismToCalculateTheNextBookingDate() {
        //
//        customerBookings.stream()
//                .noneMatch( b -> {
//
//                });
    }

}
