package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordManagementControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void addMedicalRecord_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"TestUser\",\"lastName\":\"Integration\",\"birthdate\":\"01/01/1990\",\"medications\":[\"testMed:100mg\"],\"allergies\":[\"testAllergy\"]}";
        
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
    
    @Test
    void updateMedicalRecord_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"TestUser\",\"lastName\":\"Integration\",\"birthdate\":\"01/01/1990\",\"medications\":[\"updatedMed:200mg\"],\"allergies\":[\"updatedAllergy\"]}";
        
        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
    
    @Test
    void deleteMedicalRecord_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "TestUser")
                .param("lastName", "Integration"))
                .andExpect(status().isOk());
    }
}
