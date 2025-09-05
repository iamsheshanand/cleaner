package com.cleaner.mapper;

import com.cleaner.enums.Frequency;
import com.cleaner.enums.LoyaltyScheme;
import com.cleaner.records.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CustomersMapper {
    Logger logger = LoggerFactory.getLogger(CustomersMapper.class);


    @Mapping(source = "frequency", target = "frequency", qualifiedByName = "frequencyToString")
    @Mapping(source = "loyaltyScheme", target = "loyaltyScheme", qualifiedByName = "loyaltySchemeToString")
    com.cleaner.entity.Customer toEntity(Customer customer);

    @Mapping(source = "frequency", target = "frequency", qualifiedByName = "stringToFrequency")
    @Mapping(source = "loyaltyScheme", target = "loyaltyScheme", qualifiedByName = "stringToLoyaltyScheme")
    Customer toRecord(com.cleaner.entity.Customer customer);

    @Named("stringToFrequency")
    default Frequency stringToFrequency(String frequency) {
        if (frequency == null) {
            return null;
        }
        String trimmed = frequency.trim().toLowerCase();
        logger.debug("Processing frequency: original='{}', trimmed='{}'", frequency, trimmed);
        return switch (frequency.trim().toLowerCase()) {
            case "2 weeks" -> Frequency.BI_WEEKLY;
            case "" -> Frequency.NONE;
            case "4 weeks" -> Frequency.MONTHLY;
            default -> throw new IllegalArgumentException("Unknown frequency: " + frequency);
        };
    }

    @Named("stringToLoyaltyScheme")
    default LoyaltyScheme stringToLoyaltyScheme(String loyaltyScheme) {
        if (loyaltyScheme == null) {
            return null;
        }
        return switch (loyaltyScheme.trim().toLowerCase()) {
            case "yes" -> LoyaltyScheme.LOYAL;
            case "", "no" -> LoyaltyScheme.STANDARD;
            default -> throw new IllegalArgumentException("Unknown loyalty scheme: " + loyaltyScheme);
        };
    }

    @Named("frequencyToString")
    default String frequencyToString(Frequency frequency) {
        if (frequency == null) {
            return null;
        }

        return switch (frequency) {
            case BI_WEEKLY -> "2 weeks";
            case NONE -> "";
            case MONTHLY -> "4 weeks";
        };
    }

    @Named("loyaltySchemeToString")
    default String loyaltySchemeToString(LoyaltyScheme loyaltyScheme) {
        if (loyaltyScheme == null) {
            return null;
        }
        return switch (loyaltyScheme) {
            case LOYAL -> "Yes";
            case STANDARD -> "";
        };
    }
}
