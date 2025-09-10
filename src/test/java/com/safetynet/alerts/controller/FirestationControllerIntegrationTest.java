package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllFirestations_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
    }

    @Test
    void getFirestationByAddress_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/firestations/address/address"))
                .andExpect(status().isOk());
    }

    @Test
    void addFirestation_shouldReturnOk() throws Exception {
        String json = "{\"address\":\"address\"}";
        mockMvc.perform(post("/firestations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateFirestation_shouldReturnOk() throws Exception {
        String json = "{\"address\":\"address\"}";
        mockMvc.perform(put("/firestations/address/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFirestation_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/firestations/address/address"))
                .andExpect(status().isOk());
    }
}
