package com.cleaner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_booking")
public class CustomerBooking {
    @Id
    @Column(name = "booking_number")
    private Integer bookingNumber;
    @Column(name = "customer_number", insertable = false, updatable = false)
    private Integer customerNumber;
    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CustomerBooking that = (CustomerBooking) o;

        return new EqualsBuilder()
                .append(bookingNumber, that.bookingNumber)
                .append(customerNumber, that.customerNumber).append(date, that.date)
                .append(customer, that.customer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(bookingNumber)
                .append(customerNumber).append(date)
                .append(customer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("bookingNumber", bookingNumber)
                .append("customerNumber", customerNumber)
                .append("date", date)
                .append("customer", customer)
                .toString();
    }
}

