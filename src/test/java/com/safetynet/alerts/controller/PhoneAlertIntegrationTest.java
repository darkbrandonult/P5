package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneAlertIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testPhoneAlertEndpoint() {
        // Test the actual HTTP endpoint
        String url = "http://localhost:" + port + "/phoneAlert?firestation=2";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
        
        assertEquals(200, response.getStatusCodeValue());
        
        // The response should not be empty
        String body = response.getBody();
        assertNotNull(body);
        
        // Should contain phone numbers in JSON format
        assertTrue(body.contains("phone"), "Response should contain phone numbers");
        assertTrue(body.length() > 10, "Response should not be empty");
    }
}
