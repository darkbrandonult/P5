package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
        personService.getAllPersons().clear();
    }

    @Test
    void addAndGetPerson() {
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
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
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
        personService.addPerson(person);
        Person updated = new Person(); updated.setFirstName("John"); updated.setLastName("Doe"); updated.setAddress("123 St");
        personService.updatePerson("John", "Doe", updated);
        Person found = personService.getPerson("John", "Doe");
        assertEquals("123 St", found.getAddress());
    }

    @Test
    void deletePerson_success() {
        Person person = new Person(); person.setFirstName("John"); person.setLastName("Doe");
        personService.addPerson(person);
        personService.deletePerson("John", "Doe");
        assertNull(personService.getPerson("John", "Doe"));
    }
}
