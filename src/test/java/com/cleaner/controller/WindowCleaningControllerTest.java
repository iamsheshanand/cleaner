package com.cleaner.controller;

import com.cleaner.service.interfaces.CleaningService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ActiveProfiles("test")
class WindowCleaningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CleaningService windowCleaningService;

    @Test
    void shouldTestGetCleaningCostForBookingWithValidBookingNumber() throws Exception {
        Integer bookingNumber = 1;
        when(windowCleaningService.calculateTotalCostForSpecifiedBookingNumber(bookingNumber)).thenReturn(10);

        String response = mockMvc.perform(
                        get("/cleaning/booking/{bookingNumber}/cost/total", bookingNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(10))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(Integer.parseInt(response)).isEqualTo(10);
    }

    @Test
    void shouldTestTotalQuantityOfWindowCleanedForSpecifiedDate() throws Exception {
        LocalDate specifiedDate = LocalDate.parse("2025-10-01");
        when(windowCleaningService
                .calculateTotalQuantityOfWindowsCleanedForASpecifiedDate(specifiedDate))
                .thenReturn(10);

        String response = mockMvc.perform(
                        get("/cleaning/windows/{specifiedDate}/total", specifiedDate))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(10))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(Integer.parseInt(response)).isEqualTo(10);

    }

}
