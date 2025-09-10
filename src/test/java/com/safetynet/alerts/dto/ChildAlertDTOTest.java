package com.safetynet.alerts.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Person;

class ChildAlertDTOTest {

    @Test
    void testDefaultConstructor() {
        ChildAlertDTO dto = new ChildAlertDTO();
        assertNotNull(dto);
        assertNull(dto.getChildren());
        assertNull(dto.getAdults());
    }

    @Test
    void testParameterizedConstructor() {
        List<Person> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        
        ChildAlertDTO dto = new ChildAlertDTO(children, adults);
        
        assertEquals(children, dto.getChildren());
        assertEquals(adults, dto.getAdults());
    }

    @Test
    void testGettersAndSetters() {
        ChildAlertDTO dto = new ChildAlertDTO();
        List<Person> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        
        dto.setChildren(children);
        dto.setAdults(adults);
        
        assertEquals(children, dto.getChildren());
        assertEquals(adults, dto.getAdults());
    }

    @Test
    void testEqualsAndHashCode() {
        Person child1 = new Person();
        child1.setFirstName("John");
        
        Person child2 = new Person();
        child2.setFirstName("Jane");
        
        List<Person> children1 = List.of(child1);
        List<Person> children2 = List.of(child2);
        List<Person> adults = new ArrayList<>();
        
        ChildAlertDTO dto1 = new ChildAlertDTO(children1, adults);
        ChildAlertDTO dto2 = new ChildAlertDTO(children1, adults);
        ChildAlertDTO dto3 = new ChildAlertDTO(children2, adults);
        
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotNull(dto1);
        assertEquals(dto1, dto1);
    }

    @Test
    void testToString() {
        List<Person> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        
        ChildAlertDTO dto = new ChildAlertDTO(children, adults);
        String toString = dto.toString();
        
        assertNotNull(toString);
        assertTrue(toString.length() > 0);
    }

    @Test
    void testWithPersonObjects() {
        Person child = new Person();
        child.setFirstName("John");
        child.setLastName("Doe");
        child.setAddress("123 Main St");
        child.setCity("Culver");
        child.setZip("97451");
        child.setPhone("123-456-7890");
        child.setEmail("john@email.com");
        
        Person adult = new Person();
        adult.setFirstName("Jane");
        adult.setLastName("Doe");
        adult.setAddress("123 Main St");
        adult.setCity("Culver");
        adult.setZip("97451");
        adult.setPhone("123-456-7891");
        adult.setEmail("jane@email.com");
        
        List<Person> children = List.of(child);
        List<Person> adults = List.of(adult);
        
        ChildAlertDTO dto = new ChildAlertDTO(children, adults);
        
        assertEquals(1, dto.getChildren().size());
        assertEquals(1, dto.getAdults().size());
        assertEquals("John", dto.getChildren().get(0).getFirstName());
        assertEquals("Jane", dto.getAdults().get(0).getFirstName());
    }

    @Test
    void testNullHandling() {
        ChildAlertDTO dto = new ChildAlertDTO();
        
        dto.setChildren(null);
        dto.setAdults(null);
        
        assertNull(dto.getChildren());
        assertNull(dto.getAdults());
    }

    @Test
    void testEqualsWithDifferentData() {
        Person child1 = new Person();
        child1.setFirstName("John");
        child1.setLastName("Doe");
        
        Person child2 = new Person();
        child2.setFirstName("Jane");
        child2.setLastName("Smith");
        
        ChildAlertDTO dto1 = new ChildAlertDTO(List.of(child1), new ArrayList<>());
        ChildAlertDTO dto2 = new ChildAlertDTO(List.of(child2), new ArrayList<>());
        
        assertNotEquals(dto1, dto2);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());
    }
}
