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
class PersonManagementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addPerson_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"123 Main St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-1234\",\"email\":\"john@email.com\"}";
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"456 Oak St\",\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-5678\",\"email\":\"john.updated@email.com\"}";
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deletePerson_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/person")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().isOk());
    }
}
