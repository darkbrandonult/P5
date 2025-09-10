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

import com.safetynet.alerts.model.Person;

class FirestationCoverageDTOTest {

    @Test
    void testDefaultConstructor() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        assertNotNull(dto);
        assertNull(dto.getPersons());
        assertEquals(0, dto.getAdultCount());
        assertEquals(0, dto.getChildCount());
    }

    @Test
    void testParameterizedConstructor() {
        List<Person> persons = new ArrayList<>();
        
        FirestationCoverageDTO dto = new FirestationCoverageDTO(persons, 5, 3);
        
        assertEquals(persons, dto.getPersons());
        assertEquals(5, dto.getChildCount());
        assertEquals(3, dto.getAdultCount());
    }

    @Test
    void testGettersAndSetters() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        List<Person> persons = new ArrayList<>();
        
        dto.setPersons(persons);
        dto.setAdultCount(10);
        dto.setChildCount(5);
        
        assertEquals(persons, dto.getPersons());
        assertEquals(10, dto.getAdultCount());
        assertEquals(5, dto.getChildCount());
    }

    @Test
    void testEqualsAndHashCode() {
        List<Person> persons = new ArrayList<>();
        
        FirestationCoverageDTO dto1 = new FirestationCoverageDTO(persons, 5, 3);
        FirestationCoverageDTO dto2 = new FirestationCoverageDTO(persons, 5, 3);
        FirestationCoverageDTO dto3 = new FirestationCoverageDTO(persons, 10, 3);
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "string");
        assertEquals(dto1, dto1);
    }

    @Test
    void testToString() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO(new ArrayList<>(), 5, 3);
        String toString = dto.toString();
        
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    void testWithPersonList() {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        
        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        
        List<Person> persons = List.of(person1, person2);
        FirestationCoverageDTO dto = new FirestationCoverageDTO(persons, 1, 1);
        
        assertEquals(2, dto.getPersons().size());
        assertEquals("John", dto.getPersons().get(0).getFirstName());
        assertEquals("Jane", dto.getPersons().get(1).getFirstName());
        assertEquals(1, dto.getChildCount());
        assertEquals(1, dto.getAdultCount());
    }

    @Test
    void testNullPersonsHandling() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        dto.setPersons(null);
        
        assertNull(dto.getPersons());
    }

    @Test
    void testEqualsWithDifferentCounts() {
        List<Person> persons = new ArrayList<>();
        
        FirestationCoverageDTO dto1 = new FirestationCoverageDTO(persons, 5, 3);
        FirestationCoverageDTO dto2 = new FirestationCoverageDTO(persons, 5, 4);
        FirestationCoverageDTO dto3 = new FirestationCoverageDTO(persons, 6, 3);
        
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    void testZeroCounts() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO(new ArrayList<>(), 0, 0);
        
        assertEquals(0, dto.getChildCount());
        assertEquals(0, dto.getAdultCount());
        assertTrue(dto.getPersons().isEmpty());
    }
}
