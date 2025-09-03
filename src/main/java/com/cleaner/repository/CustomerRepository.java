package com.cleaner.repository;

import com.cleaner.enums.Frequency;
import com.cleaner.enums.LoyaltyScheme;
import com.cleaner.records.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    public List<Customer> findAllCustomers() {
        Customer john = new Customer(1, "John", 10, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);
        Customer paul = new Customer(2, "Paul", 5, LoyaltyScheme.STANDARD, Frequency.NONE);
        Customer ringo = new Customer(3, "Ringo", 12, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);
        Customer george = new Customer(4, "George", 4, LoyaltyScheme.LOYAL, Frequency.BI_WEEKLY);
        return List.of(john, paul, ringo, george);
    }
}
