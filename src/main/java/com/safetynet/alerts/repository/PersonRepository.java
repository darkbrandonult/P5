package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import com.safetynet.alerts.model.Person;

/**
 * Repository interface for Person entity operations.
 * Defines the contract for data access operations on Person objects.
 */
public interface PersonRepository {
    
    /**
     * Retrieve all persons from the data source.
     * @return List of all persons
     */
    List<Person> findAll();
    
    /**
     * Find a person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return Optional containing the person if found, empty otherwise
     */
    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Find all persons living at a specific address.
     * @param address the address to search for
     * @return List of persons at the given address
     */
    List<Person> findByAddress(String address);
    
    /**
     * Find all persons living in a specific city.
     * @param city the city to search for
     * @return List of persons in the given city
     */
    List<Person> findByCity(String city);
    
    /**
     * Save a new person to the data source.
     * @param person the person to save
     * @return the saved person
     */
    Person save(Person person);
    
    /**
     * Update an existing person in the data source.
     * @param person the person with updated information
     * @return the updated person
     */
    Person update(Person person);
    
    /**
     * Delete a person by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Check if a person exists by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if person exists, false otherwise
     */
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}