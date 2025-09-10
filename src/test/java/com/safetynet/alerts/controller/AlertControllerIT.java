package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FirestationCoverageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlertControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void firestationEndpoint_returnsCorrectCoverage() throws Exception {
        mockMvc.perform(get("/firestation")
                .param("stationNumber", "4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.childCount").isNumber())
                .andExpect(jsonPath("$.adultCount").isNumber());
    }
}
