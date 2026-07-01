package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles business logic for Person resources.
 * Delegates all data access and persistence to DataRepository.
 */
@Service
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final DataRepository dataRepository;

    public PersonService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(dataRepository.getPersons());
    }

    public Person getPerson(String firstName, String lastName) {
        return dataRepository.getPersons().stream()
                .filter(p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public Person addPerson(Person person) {
        logger.info("Adding person: {} {}", person.getFirstName(), person.getLastName());
        dataRepository.getPersons().add(person);
        dataRepository.save();
        return person;
    }

    public Person updatePerson(String firstName, String lastName, Person updated) {
        List<Person> persons = dataRepository.getPersons();
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                persons.set(i, updated);
                logger.info("Person updated: {} {}", firstName, lastName);
                dataRepository.save();
                return updated;
            }
        }
        logger.warn("Person not found for update: {} {}", firstName, lastName);
        return null;
    }

    public void deletePerson(String firstName, String lastName) {
        boolean removed = dataRepository.getPersons().removeIf(
                p -> p.getFirstName().equals(firstName) && p.getLastName().equals(lastName));
        if (removed) {
            logger.info("Person deleted: {} {}", firstName, lastName);
            dataRepository.save();
        } else {
            logger.warn("Person not found for deletion: {} {}", firstName, lastName);
        }
    }
}
