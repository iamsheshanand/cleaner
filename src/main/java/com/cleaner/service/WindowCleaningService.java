package com.cleaner.service;

import com.cleaner.exception.WindowServiceException;
import com.cleaner.records.Customer;
import com.cleaner.records.CustomerBooking;
import com.cleaner.repository.CustomerBookingsRepository;
import com.cleaner.repository.CustomerRepository;
import com.cleaner.service.interfaces.CleaningService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class WindowCleaningService implements CleaningService {

    private final CustomerRepository customerRepository;
    private final CustomerBookingsRepository customerBookingsRepository;

    @PostConstruct
    public void init() {
        log.info("WindowCleaningService initiated");
    }

    public Integer calculateTotalCostForSpecifiedBookingNumber(Integer bookingNumber) {
          /*
            Assuming each booking is for one property then £5 for each
            booking and then add the number of windows cleaned
            for that booking
          */
        int costPerPropertyOrBooking = 5;
        List<Customer> customers = customerRepository.findAllCustomers();

        Map<Integer, Integer> customerWindowsByCustomerNumbers = getWindowsByCustomerNumbers(customers);
        Optional<CustomerBooking> customerBookingByBookingId = customerBookingsRepository
                .findCustomerBookingByBookingId(bookingNumber);

        if (customerBookingByBookingId.isEmpty()) {
            throw new WindowServiceException(String.format("No customer found for booking number %s", bookingNumber));
        }
        Integer numberOfWindowsPerBookingForACustomer = customerWindowsByCustomerNumbers
                .get(customerBookingByBookingId.get().customerNumber());

        return Integer.sum(costPerPropertyOrBooking, numberOfWindowsPerBookingForACustomer);
    }

    @Override
    public Integer calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(LocalDate specifiedDate) {
        List<Customer> customers = customerRepository.findAllCustomers();
        List<CustomerBooking> customerBookings = customerBookingsRepository.findCustomersBookingBySpecifiedDate(specifiedDate);
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


    private static Map<Integer, Integer> getWindowsByCustomerNumbers(List<Customer> customers) {
        return customers.stream()
                .collect(Collectors.toMap(Customer::customerNumber, Customer::windows));
    }
}
