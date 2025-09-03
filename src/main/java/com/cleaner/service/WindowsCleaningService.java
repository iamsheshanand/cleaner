package com.cleaner.service;

import com.cleaner.exception.WindowServiceException;
import com.cleaner.mapper.CustomersBookingsMapper;
import com.cleaner.mapper.CustomersMapper;
import com.cleaner.records.Customer;
import com.cleaner.records.CustomerBooking;
import com.cleaner.service.interfaces.CleaningService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
@Slf4j
public class WindowsCleaningService implements CleaningService {

    private final CustomersService customersService;
    private final CustomersBookingsService customersBookingsService;
    private final CustomersMapper customersMapper;
    private final CustomersBookingsMapper customersBookingsMapper;

    @PostConstruct
    public void init() {
        log.info("WindowsCleaningService initiated");
    }


    @Override
    public Integer calculateTotalCostForSpecifiedBookingNumber(Integer bookingNumber) {
        log.debug("Calculating total cost for booking number {}", bookingNumber);
        int costPerPropertyOrBooking = 5;
        List<Customer> customers = getCustomers();
        Map<Integer, Integer> customerWindowsByCustomerNumbers = getWindowsByCustomerNumbers(customers);

        Optional<CustomerBooking> customerBookingByBookingId = customersBookingsService.findByBookingNumber(bookingNumber).stream()
                .map(customersBookingsMapper::toRecord).findFirst();
        if (customerBookingByBookingId.isEmpty()) {
            throw new WindowServiceException(String.format("No customer found for booking number %s", bookingNumber));
        }
        Integer numberOfWindowsPerBookingForACustomer = customerWindowsByCustomerNumbers
                .get(customerBookingByBookingId.get().customerNumber());
        int sum = Integer.sum(costPerPropertyOrBooking, numberOfWindowsPerBookingForACustomer);
        log.debug("Total cost for customer booking number {} is {}", bookingNumber, sum);
        return sum;
    }

    @Override
    public Integer calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(LocalDate specifiedDate) {
        List<Customer> customers = getCustomers();
        List<CustomerBooking> customerBookings = customersBookingsService.findAll().stream()
                .filter(customerBooking -> customerBooking.getDate().equals(specifiedDate))
                .map(customersBookingsMapper::toRecord)
                .toList();
        if (customerBookings.isEmpty()) {
            throw new WindowServiceException(String.format("No customer booking found for specified date %s", specifiedDate));
        }
        Map<Integer, Integer> customerWindowsByCustomerNumbers = getWindowsByCustomerNumbers(customers);
        return customerBookings.stream()
                .filter(customerBooking -> customerBooking.date()
                        .equals(specifiedDate))
                .map(customerBooking ->
                        customerWindowsByCustomerNumbers.get(customerBooking.customerNumber()))
                .mapToInt(Integer::intValue).sum();
    }

    private List<Customer> getCustomers() {
        return  customersService.findAll().stream()
                .map(customersMapper::toRecord)
                .toList();
    }


    private static Map<Integer, Integer> getWindowsByCustomerNumbers(List<Customer> customers) {
        return customers.stream()
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));
    }

}
