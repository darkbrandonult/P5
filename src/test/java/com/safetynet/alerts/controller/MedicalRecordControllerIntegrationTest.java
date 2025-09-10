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
class MedicalRecordControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllMedicalRecords_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/medicalrecords"))
                .andExpect(status().isOk());
    }

    @Test
    void getMedicalRecord_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/medicalrecords/name/John/Doe"))
                .andExpect(status().isOk());
    }

    @Test
    void addMedicalRecord_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        mockMvc.perform(post("/medicalrecords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updateMedicalRecord_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        mockMvc.perform(put("/medicalrecords/name/John/Doe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMedicalRecord_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/medicalrecords/name/John/Doe"))
                .andExpect(status().isOk());
    }
}
