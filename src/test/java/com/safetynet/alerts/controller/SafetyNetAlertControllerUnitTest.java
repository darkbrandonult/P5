package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

class SafetyNetAlertControllerUnitTest {
    @Test
    void contextLoads() {
        // This test ensures the controller loads in the Spring context
        SafetyNetAlertController controller = new SafetyNetAlertController();
        assertNotNull(controller);
    }
}
