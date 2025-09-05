package com.cleaner.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customer_number")
    private Integer customerNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "windows")
    private Integer windows;
    @Column(name = "loyalty_scheme")
    private String loyaltyScheme;
    @Column(name = "frequency")
    private String frequency;


    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<CustomerBooking> bookings = new ArrayList<>();

    public void addBooking(CustomerBooking customerBooking) {
        this.bookings.add(customerBooking);
        customerBooking.setCustomer(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return new EqualsBuilder()
                .append(customerNumber, customer.customerNumber)
                .append(name, customer.name)
                .append(windows, customer.windows)
                .append(loyaltyScheme, customer.loyaltyScheme)
                .append(frequency, customer.frequency)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(customerNumber)
                .append(name)
                .append(windows)
                .append(loyaltyScheme)
                .append(frequency)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerNumber", customerNumber)
                .append("name", name)
                .append("windows", windows)
                .append("bookings", bookings)
                .append("loyaltyScheme", loyaltyScheme)
                .append("frequency", frequency)
                .toString();
    }
}
