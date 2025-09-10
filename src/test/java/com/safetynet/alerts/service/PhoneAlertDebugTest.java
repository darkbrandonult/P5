package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PhoneAlertDebugTest {

    @Autowired
    private AlertService alertService;

    @Test
    public void testPhoneAlertForFirestation2() {
        // This test will help us debug what the phoneAlert endpoint returns for firestation 2
        List<PhoneAlertDTO> result = alertService.getPhoneAlert("2");
        
        // Based on our data analysis, we expect these phone numbers:
        // - "841-874-6513" (Jonanathan Marrack at 29 15th St)
        // - "841-874-7878" (Sophia Zemicks at 892 Downing Ct)
        // - "841-874-7512" (Warren & Zach Zemicks at 892 Downing Ct)
        // - "841-874-7458" (Eric Cadigan at 951 LoneTree Rd)
        
        StringBuilder resultInfo = new StringBuilder();
        resultInfo.append("PhoneAlert result for firestation 2: ");
        resultInfo.append("Number of phone numbers found: ").append(result.size()).append(". ");
        resultInfo.append("Phone numbers: [");
        for (int i = 0; i < result.size(); i++) {
            resultInfo.append(result.get(i).getPhone());
            if (i < result.size() - 1) {
                resultInfo.append(", ");
            }
        }
        resultInfo.append("]");
        
        // Let's verify we get the expected results
        assertTrue(!result.isEmpty(), resultInfo.toString() + " - Should return at least one phone number");
        
        // Check if we have the expected phone numbers
        boolean foundJonanathan = result.stream().anyMatch(p -> "841-874-6513".equals(p.getPhone()));
        boolean foundSophia = result.stream().anyMatch(p -> "841-874-7878".equals(p.getPhone()));
        boolean foundWarrenZach = result.stream().anyMatch(p -> "841-874-7512".equals(p.getPhone()));
        boolean foundEric = result.stream().anyMatch(p -> "841-874-7458".equals(p.getPhone()));
        
        // Verify we found all expected phone numbers
        assertTrue(foundJonanathan, "Should find Jonanathan's phone 841-874-6513");
        assertTrue(foundSophia, "Should find Sophia's phone 841-874-7878");
        assertTrue(foundWarrenZach, "Should find Warren/Zach's phone 841-874-7512");
        assertTrue(foundEric, "Should find Eric's phone 841-874-7458");
        
        assertEquals(4, result.size(), "Should return exactly 4 phone numbers for firestation 2");
    }
}
