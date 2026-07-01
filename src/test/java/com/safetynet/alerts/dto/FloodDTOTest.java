package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FloodDTOTest {
    @Test
    void allGettersAndSetters() {
        FloodDTO dto = new FloodDTO("John", "Doe", "555", 40, List.of("med1"), List.of("allergy1"));
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("555", dto.getPhone());
        assertEquals(40, dto.getAge());
        assertEquals(List.of("med1"), dto.getMedications());
        assertEquals(List.of("allergy1"), dto.getAllergies());

        dto.setFirstName("Jane"); assertEquals("Jane", dto.getFirstName());
        dto.setLastName("Smith"); assertEquals("Smith", dto.getLastName());
        dto.setPhone("999"); assertEquals("999", dto.getPhone());
        dto.setAge(25); assertEquals(25, dto.getAge());
        dto.setMedications(List.of("x")); assertEquals(List.of("x"), dto.getMedications());
        dto.setAllergies(List.of("y")); assertEquals(List.of("y"), dto.getAllergies());
    }

    @Test
    void defaultConstructor() {
        FloodDTO dto = new FloodDTO();
        assertNull(dto.getFirstName());
    }
}
