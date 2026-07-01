package com.safetynet.alerts.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SafetyNetAlertControllerTest {
    @InjectMocks
    private SafetyNetAlertController controller;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void index_returnsMessage() {
        assertEquals("SafetyNetAlert World!", controller.index());
    }

    @Test
    void calc_returnsCorrectSum() {
        ResponseEntity<Map<String, Object>> response = controller.calc(3, 5);
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(3, body.get("left"));
        assertEquals(5, body.get("right"));
        assertEquals(8, body.get("answer"));
    }

    @Test
    void calc_withNegativeNumbers() {
        ResponseEntity<Map<String, Object>> response = controller.calc(-2, 7);
        assertEquals(5, response.getBody().get("answer"));
    }
}
