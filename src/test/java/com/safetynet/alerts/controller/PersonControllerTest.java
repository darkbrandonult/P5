package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {
    @Mock
    private PersonService personService;
    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersons_returnsList() {
        Person p1 = new Person(); p1.setFirstName("John"); p1.setLastName("Doe");
        Person p2 = new Person(); p2.setFirstName("Jane"); p2.setLastName("Smith");
        List<Person> persons = Arrays.asList(p1, p2);
        when(personService.getAllPersons()).thenReturn(persons);
        ResponseEntity<List<Person>> response = personController.getAllPersons();
        assertNotNull(response.getBody());
        List<Person> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        verify(personService).getAllPersons();
    }

    @Test
    void getPerson_found() {
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
        when(personService.getPerson("John", "Doe")).thenReturn(person);
        ResponseEntity<Person> response = personController.getPerson("John", "Doe");
        assertNotNull(response.getBody());
        Person body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        assertEquals("Doe", body.getLastName());
    }

    @Test
    void getPerson_notFound_returnsEmpty() {
        when(personService.getPerson("Foo", "Bar")).thenReturn(null);
        ResponseEntity<Person> response = personController.getPerson("Foo", "Bar");
        assertNotNull(response.getBody());
        Person body = response.getBody();
        assertNotNull(body);
        assertTrue(body.getFirstName() == null || body.getFirstName().isEmpty());
    }

    @Test
    void addPerson_success() {
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
        when(personService.addPerson(person)).thenReturn(person);
        ResponseEntity<Person> response = personController.addPerson(person);
        assertNotNull(response.getBody());
        Person body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        verify(personService).addPerson(person);
    }

    @Test
    void updatePerson_success() {
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
        when(personService.updatePerson(eq("John"), eq("Doe"), any(Person.class))).thenReturn(person);
        ResponseEntity<Person> response = personController.updatePerson("John", "Doe", person);
        assertNotNull(response.getBody());
        Person body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        verify(personService).updatePerson("John", "Doe", person);
    }

    @Test
    void deletePerson_success() {
        doNothing().when(personService).deletePerson("John", "Doe");
        ResponseEntity<Void> response = personController.deletePerson("John", "Doe");
        assertEquals(200, response.getStatusCodeValue());
        verify(personService).deletePerson("John", "Doe");
    }
}
