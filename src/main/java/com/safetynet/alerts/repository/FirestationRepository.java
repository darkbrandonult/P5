package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import com.safetynet.alerts.model.Firestation;

/**
 * Repository interface for Firestation entity operations.
 * Defines the contract for data access operations on Firestation objects.
 */
public interface FirestationRepository {
    
    /**
     * Retrieve all firestations from the data source.
     * @return List of all firestations
     */
    List<Firestation> findAll();
    
    /**
     * Find a firestation by address.
     * @param address the address to search for
     * @return Optional containing the firestation if found, empty otherwise
     */
    Optional<Firestation> findByAddress(String address);
    
    /**
     * Find all firestations by station number.
     * @param stationNumber the station number to search for
     * @return List of firestations with the given station number
     */
    List<Firestation> findByStationNumber(String stationNumber);
    
    /**
     * Find all addresses covered by a specific station number.
     * @param stationNumber the station number
     * @return List of addresses covered by the station
     */
    List<String> findAddressesByStationNumber(String stationNumber);
    
    /**
     * Save a new firestation to the data source.
     * @param firestation the firestation to save
     * @return the saved firestation
     */
    Firestation save(Firestation firestation);
    
    /**
     * Update an existing firestation in the data source.
     * @param firestation the firestation with updated information
     * @return the updated firestation
     */
    Firestation update(Firestation firestation);
    
    /**
     * Delete a firestation by address.
     * @param address the address of the firestation to delete
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteByAddress(String address);
    
    /**
     * Check if a firestation exists at a specific address.
     * @param address the address to check
     * @return true if firestation exists, false otherwise
     */
    boolean existsByAddress(String address);
}