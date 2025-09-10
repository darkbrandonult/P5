package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AlertControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getFirestationCoverage_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getChildAlert_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    void getPhoneAlert_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "2"))
                .andExpect(status().isOk());
    }

    @Test
    void getFireInfo_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/fire").param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    void getFloodStations_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                .andExpect(status().isOk());
    }
}
