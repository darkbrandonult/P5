package com.safetynet.alerts.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@WebMvcTest(MedicalRecordManagementController.class)
public class MedicalRecordManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPostMedicalRecord() throws Exception {
        MedicalRecord testRecord = new MedicalRecord();
        testRecord.setFirstName("John");
        testRecord.setLastName("Doe");
        testRecord.setBirthdate("01/01/1980");
        testRecord.setMedications(List.of("medication1"));
        testRecord.setAllergies(List.of("allergy1"));
        
        Mockito.when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class)))
                .thenReturn(testRecord);

        String requestBody = objectMapper.writeValueAsString(testRecord);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testPutMedicalRecord() throws Exception {
        MedicalRecord testRecord = new MedicalRecord();
        testRecord.setFirstName("John");
        testRecord.setLastName("Doe");
        testRecord.setBirthdate("01/01/1980");
        testRecord.setMedications(List.of("updated_medication"));
        testRecord.setAllergies(List.of("updated_allergy"));
        
        Mockito.when(medicalRecordService.updateMedicalRecord(anyString(), anyString(), any(MedicalRecord.class)))
                .thenReturn(testRecord);

        String requestBody = objectMapper.writeValueAsString(testRecord);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        Mockito.doNothing().when(medicalRecordService).deleteMedicalRecord("John", "Doe");

        mockMvc.perform(delete("/medicalRecord")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk());
    }
}
