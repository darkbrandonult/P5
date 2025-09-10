package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FireDTOTest {

    @Test
    void testDefaultConstructor() {
        FireDTO dto = new FireDTO();
        assertNotNull(dto);
        assertNull(dto.getLastName());
        assertEquals(0, dto.getAge());
        assertNull(dto.getPhone());
        assertNull(dto.getMedications());
        assertNull(dto.getAllergies());
    }

    @Test
    void testParameterizedConstructor() {
        List<String> medications = List.of("med1", "med2");
        List<String> allergies = List.of("allergy1", "allergy2");
        
        FireDTO dto = new FireDTO("Doe", 30, "123-456-7890", medications, allergies);
        
        assertEquals("Doe", dto.getLastName());
        assertEquals(30, dto.getAge());
        assertEquals("123-456-7890", dto.getPhone());
        assertEquals(medications, dto.getMedications());
        assertEquals(allergies, dto.getAllergies());
    }

    @Test
    void testGettersAndSetters() {
        FireDTO dto = new FireDTO();
        List<String> medications = new ArrayList<>();
        List<String> allergies = new ArrayList<>();
        
        dto.setLastName("Smith");
        dto.setAge(25);
        dto.setPhone("987-654-3210");
        dto.setMedications(medications);
        dto.setAllergies(allergies);
        
        assertEquals("Smith", dto.getLastName());
        assertEquals(25, dto.getAge());
        assertEquals("987-654-3210", dto.getPhone());
        assertEquals(medications, dto.getMedications());
        assertEquals(allergies, dto.getAllergies());
    }

    @Test
    void testEqualsAndHashCode() {
        List<String> medications = List.of("med1");
        List<String> allergies = List.of("allergy1");
        
        FireDTO dto1 = new FireDTO("Doe", 30, "123-456-7890", medications, allergies);
        FireDTO dto2 = new FireDTO("Doe", 30, "123-456-7890", medications, allergies);
        FireDTO dto3 = new FireDTO("Smith", 30, "123-456-7890", medications, allergies);
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "string");
        assertEquals(dto1, dto1);
    }

    @Test
    void testToString() {
        FireDTO dto = new FireDTO("Doe", 30, "123-456-7890", List.of(), List.of());
        String toString = dto.toString();
        
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    void testWithNullValues() {
        FireDTO dto = new FireDTO();
        
        dto.setLastName(null);
        dto.setPhone(null);
        dto.setMedications(null);
        dto.setAllergies(null);
        
        assertNull(dto.getLastName());
        assertNull(dto.getPhone());
        assertNull(dto.getMedications());
        assertNull(dto.getAllergies());
    }

    @Test
    void testWithEmptyCollections() {
        List<String> emptyMedications = new ArrayList<>();
        List<String> emptyAllergies = new ArrayList<>();
        
        FireDTO dto = new FireDTO("Doe", 35, "555-0123", emptyMedications, emptyAllergies);
        
        assertTrue(dto.getMedications().isEmpty());
        assertTrue(dto.getAllergies().isEmpty());
    }

    @Test
    void testEqualsWithDifferentAges() {
        List<String> medications = List.of("med1");
        List<String> allergies = List.of("allergy1");
        
        FireDTO dto1 = new FireDTO("Doe", 30, "123-456-7890", medications, allergies);
        FireDTO dto2 = new FireDTO("Doe", 31, "123-456-7890", medications, allergies);
        
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}
