package com.cleaner.mapper;

import com.cleaner.records.Customer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CustomersMapper {

    com.cleaner.entity.Customer toEntity(Customer customer);

    Customer toRecord(com.cleaner.entity.Customer customer);
}
