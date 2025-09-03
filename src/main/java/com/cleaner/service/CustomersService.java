package com.cleaner.service;

import com.cleaner.entity.Customer;
import com.cleaner.repository.CustomersRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomersService {
    private CustomersRepository customersRepository;

    public void save(Customer customer) {
        customersRepository.save(customer);
    }

    public void saveAll(List<Customer> customers) {
        customersRepository.saveAll(customers);
    }

    public List<Customer> findAll() {
        return customersRepository.findAll();
    }

    public Optional<Customer> findById(long id) {
        return customersRepository.findById(id);
    }

    @PostConstruct
    public void init() {
        log.info("CustomersService initiated");
    }

}
