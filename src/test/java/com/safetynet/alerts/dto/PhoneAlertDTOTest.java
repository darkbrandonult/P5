package com.safetynet.alerts.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class PhoneAlertDTOTest {

    @Test
    void testDefaultConstructor() {
        PhoneAlertDTO dto = new PhoneAlertDTO();
        assertNotNull(dto);
        assertNull(dto.getPhone());
    }

    @Test
    void testParameterizedConstructor() {
        PhoneAlertDTO dto = new PhoneAlertDTO("123-456-7890");
        
        assertEquals("123-456-7890", dto.getPhone());
    }

    @Test
    void testGettersAndSetters() {
        PhoneAlertDTO dto = new PhoneAlertDTO();
        
        dto.setPhone("987-654-3210");
        
        assertEquals("987-654-3210", dto.getPhone());
    }

    @Test
    void testSetNullPhone() {
        PhoneAlertDTO dto = new PhoneAlertDTO("123-456-7890");
        dto.setPhone(null);
        
        assertNull(dto.getPhone());
    }

    @Test
    void testSetEmptyPhone() {
        PhoneAlertDTO dto = new PhoneAlertDTO();
        dto.setPhone("");
        
        assertEquals("", dto.getPhone());
    }

    @Test
    void testToString() {
        PhoneAlertDTO dto = new PhoneAlertDTO("123-456-7890");
        String toString = dto.toString();
        
        assertNotNull(toString);
        assertFalse(toString.isEmpty());
    }

    @Test
    void testObjectEquality() {
        PhoneAlertDTO dto1 = new PhoneAlertDTO("123-456-7890");
        
        // Test object identity
        assertEquals(dto1, dto1);
        
        // Test basic object behavior
        assertNotNull(dto1);
        assertNotNull(dto1.toString());
    }
}
