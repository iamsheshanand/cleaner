package com.cleaner.mapper;

import com.cleaner.records.CustomerBooking;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CustomersBookingsMapper {

    com.cleaner.entity.CustomerBooking toEntity(CustomerBooking customerBooking);

    CustomerBooking toRecord(com.cleaner.entity.CustomerBooking customerBooking);
}
