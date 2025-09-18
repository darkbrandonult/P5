package com.safetynet.alerts.repository.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.JsonFileDataRepository;
import com.safetynet.alerts.repository.PersonRepository;

@Repository
public class PersonRepositoryImpl extends JsonFileDataRepository<Person> implements PersonRepository {
    private static final Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);

    @Override
    protected String getJsonNodeName() {
        return "persons";
    }

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public List<Person> findAll() {
        logger.debug("Finding all persons");
        return getAllEntities();
    }

    @Override
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Finding person by name: {} {}", firstName, lastName);
        return getAllEntities().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public List<Person> findByAddress(String address) {
        logger.debug("Finding persons by address: {}", address);
        return getAllEntities().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }

    @Override
    public List<Person> findByCity(String city) {
        logger.debug("Finding persons by city: {}", city);
        return getAllEntities().stream()
                .filter(person -> person.getCity().equals(city))
                .toList();
    }

    @Override
    public Person save(Person person) {
        logger.info("Saving person: {} {}", person.getFirstName(), person.getLastName());
        
        // Check if person already exists (update case)
        Optional<Person> existing = findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        if (existing.isPresent()) {
            return update(person);
        }
        
        // Add new person
        addEntity(person);
        saveToFile();
        return person;
    }

    @Override
    public Person update(Person person) {
        logger.info("Updating person: {} {}", person.getFirstName(), person.getLastName());
        
        List<Person> allPersons = getAllEntities();
        for (int i = 0; i < allPersons.size(); i++) {
            Person existing = allPersons.get(i);
            if (existing.getFirstName().equals(person.getFirstName()) && 
                existing.getLastName().equals(person.getLastName())) {
                allPersons.set(i, person);
                replaceEntities(allPersons);
                saveToFile();
                return person;
            }
        }
        
        // If not found, treat as new
        logger.warn("Person not found for update, creating new: {} {}", person.getFirstName(), person.getLastName());
        return save(person);
    }

    @Override
    public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
        logger.info("Deleting person: {} {}", firstName, lastName);
        
        List<Person> allPersons = getAllEntities();
        boolean removed = allPersons.removeIf(person -> 
            person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
        
        if (removed) {
            replaceEntities(allPersons);
            saveToFile();
            logger.info("Person deleted successfully: {} {}", firstName, lastName);
        } else {
            logger.warn("Person not found for deletion: {} {}", firstName, lastName);
        }
        
        return removed;
    }

    @Override
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Checking if person exists: {} {}", firstName, lastName);
        return findByFirstNameAndLastName(firstName, lastName).isPresent();
    }
}