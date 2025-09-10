package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PersonInfoDTOTest {

    @Test
    void testDefaultConstructor() {
        PersonInfoDTO dto = new PersonInfoDTO();
        assertNotNull(dto);
        assertNull(dto.getLastName());
        assertNull(dto.getAddress());
        assertEquals(0, dto.getAge());
        assertNull(dto.getEmail());
        assertNull(dto.getMedications());
        assertNull(dto.getAllergies());
    }

    @Test
    void testParameterizedConstructor() {
        List<String> medications = List.of("med1", "med2");
        List<String> allergies = List.of("allergy1", "allergy2");
        
        PersonInfoDTO dto = new PersonInfoDTO("John", "Doe", "123 Main St", 30, 
                                            "john@email.com", medications, allergies);
        
        assertEquals("Doe", dto.getLastName());
        assertEquals("123 Main St", dto.getAddress());
        assertEquals(30, dto.getAge());
        assertEquals("john@email.com", dto.getEmail());
        assertEquals(medications, dto.getMedications());
        assertEquals(allergies, dto.getAllergies());
    }

    @Test
    void testGettersAndSetters() {
        PersonInfoDTO dto = new PersonInfoDTO();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        
        dto.setLastName("Smith");
        dto.setAddress("456 Oak St");
        dto.setAge(25);
        dto.setEmail("smith@email.com");
        dto.setMedications(medications);
        dto.setAllergies(allergies);
        
        assertEquals("Smith", dto.getLastName());
        assertEquals("456 Oak St", dto.getAddress());
        assertEquals(25, dto.getAge());
        assertEquals("smith@email.com", dto.getEmail());
        assertEquals(medications, dto.getMedications());
        assertEquals(allergies, dto.getAllergies());
    }

    @Test
    void testToString() {
        PersonInfoDTO dto = new PersonInfoDTO("John", "Doe", "123 Main St", 30, 
                                            "john@email.com", List.of(), List.of());
        String toString = dto.toString();
        
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    void testWithNullValues() {
        PersonInfoDTO dto = new PersonInfoDTO();
        
        dto.setLastName(null);
        dto.setAddress(null);
        dto.setEmail(null);
        dto.setMedications(null);
        dto.setAllergies(null);
        
        assertNull(dto.getLastName());
        assertNull(dto.getAddress());
        assertNull(dto.getEmail());
        assertNull(dto.getMedications());
        assertNull(dto.getAllergies());
    }

    @Test
    void testWithEmptyCollections() {
        List<String> emptyMedications = new ArrayList<>();
        List<String> emptyAllergies = new ArrayList<>();
        
        PersonInfoDTO dto = new PersonInfoDTO("John", "Doe", "123 Main St", 35, 
                                            "john@email.com", emptyMedications, emptyAllergies);
        
        assertTrue(dto.getMedications().isEmpty());
        assertTrue(dto.getAllergies().isEmpty());
    }

    @Test
    void testObjectIdentity() {
        PersonInfoDTO dto = new PersonInfoDTO("John", "Doe", "123 Main St", 30, 
                                            "john@email.com", List.of(), List.of());
        
        assertEquals(dto, dto);
        assertNotNull(dto);
        assertNotNull(dto.toString());
    }
}
