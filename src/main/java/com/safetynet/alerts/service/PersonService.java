package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class PersonService implements PersonServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPersons() {
        logger.info("Fetching all persons");
        return personRepository.findAll();
    }

    @Override
    public Person addPerson(Person person) {
        logger.info("Adding person: {} {}", person.getFirstName(), person.getLastName());
        return personRepository.save(person);
    }

    @Override
    public Person getPerson(String firstName, String lastName) {
        logger.info("Fetching person: {} {}", firstName, lastName);
        Optional<Person> person = personRepository.findByFirstNameAndLastName(firstName, lastName);
        if (person.isPresent()) {
            return person.get();
        }
        logger.warn("Person not found: {} {}", firstName, lastName);
        return null;
    }

    @Override
    public Person updatePerson(String firstName, String lastName, Person person) {
        logger.info("Updating person: {} {}", firstName, lastName);
        
        // Ensure the person has the correct names
        person.setFirstName(firstName);
        person.setLastName(lastName);
        
        if (personRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            return personRepository.update(person);
        } else {
            logger.warn("Person not found for update: {} {}", firstName, lastName);
            return null;
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        logger.info("Deleting person: {} {}", firstName, lastName);
        boolean deleted = personRepository.deleteByFirstNameAndLastName(firstName, lastName);
        if (!deleted) {
            logger.warn("Person not found for deletion: {} {}", firstName, lastName);
        }
    }
}
