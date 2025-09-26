package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;

/**
 * Concrete repository class for Person entity operations.
 * Handles direct file operations for person data.
 */
@Repository
public class PersonRepository extends JsonFileDataRepository<Person> {
    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Override
    protected String getJsonNodeName() {
        return "persons";
    }

    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    /**
     * Retrieve all persons from the data source.
     * @return List of all persons
     */
    public List<Person> findAll() {
        logger.debug("Finding all persons");
        return getAllEntities();
    }
    
    /**
     * Find a person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return Optional containing the person if found, empty otherwise
     */
    public Optional<Person> findByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Finding person by name: {} {}", firstName, lastName);
        return getAllEntities().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst();
    }
    
    /**
     * Find all persons living at a specific address.
     * @param address the address to search for
     * @return List of persons at the given address
     */
    public List<Person> findByAddress(String address) {
        logger.debug("Finding persons by address: {}", address);
        return getAllEntities().stream()
                .filter(person -> person.getAddress().equals(address))
                .toList();
    }
    
    /**
     * Find all persons living in a specific city.
     * @param city the city to search for
     * @return List of persons in the given city
     */
    public List<Person> findByCity(String city) {
        logger.debug("Finding persons by city: {}", city);
        return getAllEntities().stream()
                .filter(person -> person.getCity().equals(city))
                .toList();
    }
    
    /**
     * Save a new person to the data source.
     * @param person the person to save
     * @return the saved person
     */
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
    
    /**
     * Update an existing person in the data source.
     * @param person the person with updated information
     * @return the updated person
     */
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
    
    /**
     * Delete a person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if deletion was successful, false otherwise
     */
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
    
    /**
     * Check if a person exists by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if person exists, false otherwise
     */
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Checking if person exists: {} {}", firstName, lastName);
        return findByFirstNameAndLastName(firstName, lastName).isPresent();
    }
}