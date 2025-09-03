package com.cleaner.repository;

import com.cleaner.entity.Customer;
import com.cleaner.entity.CustomerBooking;
import jakarta.persistence.EntityManager;
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
class CustomersBookingRepositoryTest {

    @Autowired
    private CustomersBookingsRepository customersBookingRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldTestCustomersBookingRepositoryFindByBookingNumber() {
        var customerBooking1 = CustomerBooking.builder()
                .customerNumber(123)
                .bookingNumber(1)
                .date(LocalDate.parse("2025-10-01"))
                .build();
        List<CustomerBooking> customersBookingList = List.of(customerBooking1);
        var customer1 = Customer.builder()
                .customerNumber(123)
                .name("name").windows(10).bookings(customersBookingList).build();



        entityManager.persist(customer1);
        Optional<CustomerBooking> customerBooking = customersBookingRepository.findByBookingNumber(1);

        assertThat(customerBooking).isNotNull();
        assertThat(customerBooking.get()).isEqualTo(customerBooking1);
        assertThat(customerBooking.get().getCustomerNumber()).isEqualTo(123);
    }

    @Test
    void shouldTestCustomersBookingRepositoryFindAll() {
        var customerBooking1 = CustomerBooking.builder()
                .customerNumber(123)
                .bookingNumber(1)
                .date(LocalDate.parse("2025-10-01"))
                .build();
        List<CustomerBooking> customersBookingList = List.of(customerBooking1);
        var customer1 = Customer.builder().customerNumber(123)
                .name("name").windows(10).bookings(customersBookingList).build();
        customerBooking1.setCustomer(customer1);


        entityManager.persist(customer1);
        customersBookingRepository.saveAll(customersBookingList);
        List<CustomerBooking> customerBookings = customersBookingRepository.findAll();


        assertThat(customerBookings).isNotNull();
        assertThat(customerBookings.get(0)).isEqualTo(customerBooking1);
        assertThat(customerBookings.get(0).getCustomerNumber()).isEqualTo(123);
    }


}
