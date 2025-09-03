package com.cleaner.repository;

import com.cleaner.entity.Customer;
import com.cleaner.entity.CustomerBooking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CustomersRepositoryTest {

    @Autowired
    private CustomersRepository customersRepository;

    @Test
    void shouldTestCustomersRepositoryFindAll() {
        var customerBooking1 = CustomerBooking.builder()
                .customerNumber(123)
                .bookingNumber(1)
                .date(LocalDate.parse("2025-10-01"))
                .build();
        List<CustomerBooking> customersBookingList = List.of(customerBooking1);
        var customer1 = Customer.builder().customerNumber(123)
                .name("name").windows(10).bookings(customersBookingList).build();
        customerBooking1.setCustomer(customer1);


        customersRepository.save(customer1);
        List<Customer> customers = customersRepository.findAll();

        assertThat(customers).isNotNull();
        assertThat(customers.size()).isEqualTo(1);
        assertThat(customers.get(0).getCustomerNumber()).isEqualTo(123);
    }

    @Test
    void shouldTestCustomersRepositorySave() {
        var customerBooking1 = CustomerBooking.builder()
                .customerNumber(123)
                .bookingNumber(1)
                .date(LocalDate.parse("2025-10-01"))
                .build();
        List<CustomerBooking> customersBookingList = List.of(customerBooking1);
        var customer1 = Customer.builder().customerNumber(123)
                .name("name").windows(10).bookings(customersBookingList).build();
        customerBooking1.setCustomer(customer1);


        customersRepository.save(customer1);
        Optional<Customer> customer = customersRepository.findById(123L);

        assertThat(customer).isNotNull();
        assertThat(customer.get()).isEqualTo(customer1);
        assertThat(customer.get().getCustomerNumber()).isEqualTo(123);
    }


    @Test
    void shouldTestCustomersRepositoryFindCustomerByCustomerNumber() {
        var customerBooking1 = CustomerBooking.builder()
                .customerNumber(123)
                .bookingNumber(1)
                .date(LocalDate.parse("2025-10-01"))
                .build();
        List<CustomerBooking> customersBookingList = List.of(customerBooking1);
        var customer1 = Customer.builder().customerNumber(123)
                .name("name").windows(10).bookings(customersBookingList).build();
        customerBooking1.setCustomer(customer1);


        customersRepository.save(customer1);
        Optional<Customer> customers = customersRepository.findById(123L);

        assertThat(customers).isNotNull();
        assertThat(customers.get()).isEqualTo(customer1);
        assertThat(customers.get().getCustomerNumber()).isEqualTo(123);
    }
}
