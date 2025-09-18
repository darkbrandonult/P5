package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    
    @Mock
    private PersonRepository personRepository;
    
    @InjectMocks
    private PersonService personService;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person();
        testPerson.setFirstName("John");
        testPerson.setLastName("Doe");
        testPerson.setAddress("123 Test St");
        testPerson.setCity("TestCity");
        testPerson.setZip("12345");
        testPerson.setPhone("555-1234");
        testPerson.setEmail("john.doe@test.com");
    }

    @Test
    void addPerson_ShouldReturnSavedPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);
        
        Person result = personService.addPerson(testPerson);
        
        assertEquals(testPerson, result);
        verify(personRepository, times(1)).save(testPerson);
    }

    @Test
    void getAllPersons_ShouldReturnAllPersons() {
        List<Person> persons = List.of(testPerson);
        when(personRepository.findAll()).thenReturn(persons);
        
        List<Person> result = personService.getAllPersons();
        
        assertEquals(persons, result);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void getPerson_WhenPersonExists_ShouldReturnPerson() {
        when(personRepository.findByFirstNameAndLastName("John", "Doe"))
            .thenReturn(Optional.of(testPerson));
        
        Person result = personService.getPerson("John", "Doe");
        
        assertEquals(testPerson, result);
        verify(personRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void getPerson_WhenPersonDoesNotExist_ShouldReturnNull() {
        when(personRepository.findByFirstNameAndLastName("John", "Doe"))
            .thenReturn(Optional.empty());
        
        Person result = personService.getPerson("John", "Doe");
        
        assertNull(result);
        verify(personRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void updatePerson_WhenPersonExists_ShouldReturnUpdatedPerson() {
        when(personRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(true);
        when(personRepository.update(any(Person.class))).thenReturn(testPerson);
        
        Person result = personService.updatePerson("John", "Doe", testPerson);
        
        assertEquals(testPerson, result);
        verify(personRepository, times(1)).existsByFirstNameAndLastName("John", "Doe");
        verify(personRepository, times(1)).update(testPerson);
    }

    @Test
    void updatePerson_WhenPersonDoesNotExist_ShouldReturnNull() {
        when(personRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(false);
        
        Person result = personService.updatePerson("John", "Doe", testPerson);
        
        assertNull(result);
        verify(personRepository, times(1)).existsByFirstNameAndLastName("John", "Doe");
        verify(personRepository, never()).update(any(Person.class));
    }

    @Test
    void deletePerson_ShouldCallRepositoryDelete() {
        when(personRepository.deleteByFirstNameAndLastName("John", "Doe")).thenReturn(true);
        
        personService.deletePerson("John", "Doe");
        
        verify(personRepository, times(1)).deleteByFirstNameAndLastName("John", "Doe");
    }
}
