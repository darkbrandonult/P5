
package com.safetynet.alerts.controller;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonControllerUnitTest {
    @Mock
    private PersonService personService;
    @InjectMocks
    private PersonController personController;

    @Test
    void getAllPersons_returnsOk() {
        List<Person> persons = new ArrayList<>();
        when(personService.getAllPersons()).thenReturn(persons);
        ResponseEntity<List<Person>> response = personController.getAllPersons();
        assertEquals(persons, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getPerson_returnsOk() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress(null);
        person.setCity(null);
        person.setZip(null);
        person.setPhone(null);
        person.setEmail(null);
        when(personService.getPerson(eq("John"), eq("Doe"))).thenReturn(person);
        ResponseEntity<Person> response = personController.getPerson("John", "Doe");
        assertEquals(person, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void addPerson_returnsOk() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress(null);
        person.setCity(null);
        person.setZip(null);
        person.setPhone(null);
        person.setEmail(null);
        when(personService.addPerson(any(Person.class))).thenReturn(person);
        ResponseEntity<Person> response = personController.addPerson(person);
        assertEquals(person, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updatePerson_returnsOk() {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress(null);
        person.setCity(null);
        person.setZip(null);
        person.setPhone(null);
        person.setEmail(null);
        when(personService.updatePerson(eq("John"), eq("Doe"), any(Person.class))).thenReturn(person);
        ResponseEntity<Person> response = personController.updatePerson("John", "Doe", person);
        assertEquals(person, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deletePerson_returnsOk() {
        doNothing().when(personService).deletePerson("John", "Doe");
        ResponseEntity<Void> response = personController.deletePerson("John", "Doe");
        assertEquals(200, response.getStatusCodeValue());
    }
}
