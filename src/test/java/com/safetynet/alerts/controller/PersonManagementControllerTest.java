package com.safetynet.alerts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonManagementControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonManagementController personManagementController;

    @Test
    void addPerson_success() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john@email.com");

        when(personService.addPerson(any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personManagementController.addPerson(person);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John", response.getBody().getFirstName());
        assertEquals("Doe", response.getBody().getLastName());
        verify(personService).addPerson(person);
    }

    @Test
    void updatePerson_success() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("john.updated@email.com");

        when(personService.updatePerson(eq("John"), eq("Doe"), any(Person.class))).thenReturn(person);

        ResponseEntity<Person> response = personManagementController.updatePerson(person);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john.updated@email.com", response.getBody().getEmail());
        verify(personService).updatePerson("John", "Doe", person);
    }

    @Test
    void deletePerson_success() {
        doNothing().when(personService).deletePerson("John", "Doe");

        ResponseEntity<Void> response = personManagementController.deletePerson("John", "Doe");

        assertEquals(200, response.getStatusCodeValue());
        verify(personService).deletePerson("John", "Doe");
    }
}
