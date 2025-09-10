package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PhoneAlertFullDebugTest {

    @Autowired
    private AlertService alertService;
    
    @Autowired
    private FirestationService firestationService;
    
    @Autowired
    private PersonService personService;

    @Test
    void debugPhoneAlertStepByStep() {
        String firestation = "2";
        
        // Step 1: Check firestations
        List<Firestation> firestations = firestationService.getAllFirestations();
        System.out.println("Total firestations: " + firestations.size());
        
        int matchingStations = 0;
        for (Firestation f : firestations) {
            if (f.getStation() != null && f.getStation().equals(firestation)) {
                matchingStations++;
                System.out.println("Found firestation 2 address: " + f.getAddress());
            }
        }
        System.out.println("Matching stations for '2': " + matchingStations);
        
        // Step 2: Check persons
        List<Person> allPersons = personService.getAllPersons();
        System.out.println("Total persons: " + allPersons.size());
        
        // Step 3: Test the full method
        List<PhoneAlertDTO> result = alertService.getPhoneAlert(firestation);
        System.out.println("Final result size: " + result.size());
        
        for (PhoneAlertDTO phone : result) {
            System.out.println("Phone: " + phone.getPhone());
        }
        
        // Verify the result
        assertNotNull(result, "PhoneAlert result should not be null");
        assertFalse(result.isEmpty(), "PhoneAlert should return phone numbers for firestation 2");
        assertTrue(result.size() >= matchingStations, "Should have at least as many phone numbers as stations found");
    }
}
