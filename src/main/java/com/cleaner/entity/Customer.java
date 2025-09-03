package com.cleaner.entity;

import jakarta.persistence.*;
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
                .append(windows, customer.windows).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(customerNumber)
                .append(name)
                .append(windows)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerNumber", customerNumber)
                .append("name", name)
                .append("windows", windows)
                .append("bookings", bookings)
                .toString();
    }
}
