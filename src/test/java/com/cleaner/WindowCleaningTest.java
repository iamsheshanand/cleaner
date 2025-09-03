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

class WindowCleaningTest {

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

        Customer john = new Customer(1, "John", 10, "", 0);
        Customer paul = new Customer(2, "Paul", 5, "", 0);
        Customer ringo = new Customer(3, "Ringo", 12, "", 0);
        Customer george = new Customer(4, "George", 4, "", 0);

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

        //map of date and booking
//        Map<LocalDate, List<CustomerBooking>> listOfBookingNumbersByDate = customerBookings.stream()
//                .collect(Collectors.groupingBy(CustomerBooking::date));

        //date filter (maybe not needed) (but to check if the date is available matching specified date)
//        Optional<LocalDate> date = listOfBookingNumbersByDate.keySet().stream()
//                .filter(c -> c.equals(LocalDate.parse(DATE_2025_10_01)))
//                .findFirst();

//        if (date.isPresent()) { // now if it does
        //get all the customer bookings by that date
//            List<CustomerBooking> customerBookingFilteredByDate = listOfBookingNumbersByDate.get(date.get());
        int sum = customerBookings.stream()
                .filter(cb -> LocalDate.parse(DATE_2025_10_01).equals(cb.date()))
                .map(cb -> customers.stream()
                        .filter(customer -> cb.customerNumber()
                                .equals(customer.customerNumber()))
                        .mapToInt(Customer::windows)
                        .sum()
                ).mapToInt(Integer::intValue).sum();


//                    .collect(Collectors.groupingBy(CustomerBooking::customerNumber));
//        Integer[] sum = {0};
//        customerNumberWithCustomerBookingsMap.forEach((k, v) -> {
//                int count = customers.stream()
//                        .filter(c -> c.customerNumber().equals(k))
//                        .map(Customer::windows)
//                        .mapToInt(Integer::intValue)
//                        .sum();
//                sum[0] = sum[0] + count;
//            });
//        }


        assertEquals(totalWindowsCleanedOnSpecifiedDate, sum);

    }


    @Test
    @DisplayName("Should test total cost for specified booking number 3")
    void shouldTestTotalCostForSpecifiedBookingNumber3() {
        /*
        Assuming each booking is for one property then £5 for each
        booking and then add the number of windows cleaned
       for that booking
       */


        Integer bookingNumber = 3;
        Integer costPerPropertyOrBooking = 5;

        /*
            Booking number 3 is with customer number 1 having 10 windows
            and has 1 booking on 2025-10-01
         */
        Integer numberOfWindowsForBooking3AndCustomer1 = 10;

        Integer expectedTotalCostPerBooking = numberOfWindowsForBooking3AndCustomer1 + costPerPropertyOrBooking;


        final List<Customer>[] matchedCustomer = new List[1];
        customerBookings.stream().filter(c -> c.bookingNumber().equals(bookingNumber))
                .forEach(cb -> {
                    matchedCustomer[0] = customers.stream()
                            .filter(customer -> customer.customerNumber()
                                    .equals(cb.customerNumber()))
                            .toList();
                });


        Integer numberOfWindowsPerBookingForACustomer = matchedCustomer[0]
                .stream()
                .mapToInt(Customer::windows)
                .sum();


        //Based on above assumption
        Integer actualTotalCostForSpecifiedBooking = numberOfWindowsPerBookingForACustomer + costPerPropertyOrBooking;

        assertEquals(expectedTotalCostPerBooking, actualTotalCostForSpecifiedBooking);

    }

}
