package com.cleaner.repository;

import com.cleaner.records.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    public List<Customer> findAllCustomers() {
        Customer john = new Customer(1, "John", 10, "", 0);
        Customer paul = new Customer(2, "Paul", 5, "", 0);
        Customer ringo = new Customer(3, "Ringo", 12, "", 0);
        Customer george = new Customer(4, "George", 4, "", 0);
        return List.of(john, paul, ringo, george);
    }
}
