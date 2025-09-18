package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import java.util.List;

/**
 * Service interface for Person operations.
 * Defines the contract for business logic operations on Person entities.
 */
public interface PersonServiceInterface {
    
    /**
     * Retrieve all persons.
     * @return List of all persons
     */
    List<Person> getAllPersons();
    
    /**
     * Add a new person.
     * @param person the person to add
     * @return the added person
     */
    Person addPerson(Person person);
    
    /**
     * Get a specific person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return the person if found, null otherwise
     */
    Person getPerson(String firstName, String lastName);
    
    /**
     * Update an existing person.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param person the updated person information
     * @return the updated person
     */
    Person updatePerson(String firstName, String lastName, Person person);
    
    /**
     * Delete a person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     */
    void deletePerson(String firstName, String lastName);
}