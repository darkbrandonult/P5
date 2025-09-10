package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChildAlertIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testChildAlertEndpoint() {
        // Test the childAlert endpoint for 1509 Culver St
        String url = "http://localhost:" + port + "/childAlert?address=1509 Culver St";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
        
        assertEquals(200, response.getStatusCodeValue());
        
        // The response should not be empty
        String body = response.getBody();
        assertNotNull(body);
        
        // Should contain children: Tenley Boyd (age 13) and Roger Boyd (age 7)
        // Should contain adults: John Boyd (age 41), Jacob Boyd (age 36), Felicia Boyd (age 39)
        assertTrue(body.contains("Tenley"));
        assertTrue(body.contains("Roger"));
        assertTrue(body.contains("children"));
        assertTrue(body.contains("adults"));
        
        // Should contain children: Tenley Boyd and Roger Boyd
        assertTrue(body.contains("Tenley") && body.contains("Roger"), "Should contain children Tenley and Roger");
    }
}
