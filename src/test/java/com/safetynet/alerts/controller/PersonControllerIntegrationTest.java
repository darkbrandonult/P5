package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPersons_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void getPerson_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/persons/name/John/Doe"))
                .andExpect(status().isOk());
    }

    @Test
    void addPerson_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void updatePerson_shouldReturnOk() throws Exception {
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\"}";
        mockMvc.perform(put("/persons/name/John/Doe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deletePerson_shouldReturnOk() throws Exception {
        mockMvc.perform(delete("/persons/name/John/Doe"))
                .andExpect(status().isOk());
    }
}
