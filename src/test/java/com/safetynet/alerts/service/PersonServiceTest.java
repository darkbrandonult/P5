package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Spy
    private PersonService personService;

    @BeforeEach
    void setUp() {
        // Initialize empty persons list
        List<Person> emptyPersons = new ArrayList<>();
        ReflectionTestUtils.setField(personService, "persons", emptyPersons);
    }
    
    private void mockSaveToFile() {
        // Mock the saveToFile method to prevent file operations during tests
        doNothing().when(personService).saveToFile();
    }

    @Test
    void addAndGetPerson() {
        mockSaveToFile();
        Person person = new Person(); 
        person.setFirstName("John"); 
        person.setLastName("Doe");
        personService.addPerson(person);
        Person found = personService.getPerson("John", "Doe");
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    void getAllPersons_emptyInitially() {
        assertTrue(personService.getAllPersons().isEmpty());
    }

    @Test
    void getPerson_notFound() {
        assertNull(personService.getPerson("Foo", "Bar"));
    }

    @Test
    void updatePerson_success() {
        mockSaveToFile();
        Person person = new Person(); 
        person.setFirstName("John"); 
        person.setLastName("Doe");
        personService.addPerson(person);
        Person updated = new Person(); 
        updated.setFirstName("John"); 
        updated.setLastName("Doe"); 
        updated.setAddress("123 St");
        personService.updatePerson("John", "Doe", updated);
        Person found = personService.getPerson("John", "Doe");
        assertEquals("123 St", found.getAddress());
    }

    @Test
    void deletePerson_success() {
        mockSaveToFile();
        Person person = new Person(); 
        person.setFirstName("John"); 
        person.setLastName("Doe");
        personService.addPerson(person);
        personService.deletePerson("John", "Doe");
        assertNull(personService.getPerson("John", "Doe"));
    }
}
