package com.safetynet.alerts.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonInfoDTOTest {
    @Test
    void allGettersAndSetters() {
        PersonInfoDTO dto = new PersonInfoDTO("John", "Doe", "123 St", 40, "j@mail.com", List.of("med1"), List.of("allergy1"));
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("123 St", dto.getAddress());
        assertEquals(40, dto.getAge());
        assertEquals("j@mail.com", dto.getEmail());
        assertEquals(List.of("med1"), dto.getMedications());
        assertEquals(List.of("allergy1"), dto.getAllergies());

        dto.setFirstName("Jane"); assertEquals("Jane", dto.getFirstName());
        dto.setLastName("Smith"); assertEquals("Smith", dto.getLastName());
        dto.setAddress("456 Ave"); assertEquals("456 Ave", dto.getAddress());
        dto.setAge(30); assertEquals(30, dto.getAge());
        dto.setEmail("s@mail.com"); assertEquals("s@mail.com", dto.getEmail());
        dto.setMedications(List.of("x")); assertEquals(List.of("x"), dto.getMedications());
        dto.setAllergies(List.of("y")); assertEquals(List.of("y"), dto.getAllergies());
    }

    @Test
    void defaultConstructor() {
        PersonInfoDTO dto = new PersonInfoDTO();
        assertNull(dto.getFirstName());
    }
}
