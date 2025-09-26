package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Firestation;

/**
 * Concrete repository class for Firestation entity operations.
 * Handles direct file operations for firestation data.
 */
@Repository
public class FirestationRepository extends JsonFileDataRepository<Firestation> {
    private static final Logger logger = LoggerFactory.getLogger(FirestationRepository.class);

    @Override
    protected String getJsonNodeName() {
        return "firestations";
    }

    @Override
    protected Class<Firestation> getEntityClass() {
        return Firestation.class;
    }

    /**
     * Retrieve all firestations from the data source.
     * @return List of all firestations
     */
    public List<Firestation> findAll() {
        logger.debug("Finding all firestations");
        return getAllEntities();
    }
    
    /**
     * Find a firestation by address.
     * @param address the address to search for
     * @return Optional containing the firestation if found, empty otherwise
     */
    public Optional<Firestation> findByAddress(String address) {
        logger.debug("Finding firestation by address: {}", address);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }
    
    /**
     * Find all firestations by station number.
     * @param stationNumber the station number to search for
     * @return List of firestations with the given station number
     */
    public List<Firestation> findByStationNumber(String stationNumber) {
        logger.debug("Finding firestations by station number: {}", stationNumber);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .toList();
    }
    
    /**
     * Find all addresses covered by a specific station number.
     * @param stationNumber the station number
     * @return List of addresses covered by the station
     */
    public List<String> findAddressesByStationNumber(String stationNumber) {
        logger.debug("Finding addresses by station number: {}", stationNumber);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();
    }
    
    /**
     * Save a new firestation to the data source.
     * @param firestation the firestation to save
     * @return the saved firestation
     */
    public Firestation save(Firestation firestation) {
        logger.info("Saving firestation: address={}, station={}", firestation.getAddress(), firestation.getStation());
        
        // Check if firestation already exists for this address (update case)
        Optional<Firestation> existing = findByAddress(firestation.getAddress());
        if (existing.isPresent()) {
            return update(firestation);
        }
        
        // Add new firestation
        addEntity(firestation);
        saveToFile();
        return firestation;
    }
    
    /**
     * Update an existing firestation in the data source.
     * @param firestation the firestation with updated information
     * @return the updated firestation
     */
    public Firestation update(Firestation firestation) {
        logger.info("Updating firestation: address={}, station={}", firestation.getAddress(), firestation.getStation());
        
        List<Firestation> allFirestations = getAllEntities();
        for (int i = 0; i < allFirestations.size(); i++) {
            Firestation existing = allFirestations.get(i);
            if (existing.getAddress().equals(firestation.getAddress())) {
                allFirestations.set(i, firestation);
                replaceEntities(allFirestations);
                saveToFile();
                return firestation;
            }
        }
        
        // If not found, treat as new
        logger.warn("Firestation not found for update, creating new: address={}", firestation.getAddress());
        return save(firestation);
    }
    
    /**
     * Delete a firestation by address.
     * @param address the address of the firestation to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteByAddress(String address) {
        logger.info("Deleting firestation by address: {}", address);
        
        List<Firestation> allFirestations = getAllEntities();
        boolean removed = allFirestations.removeIf(firestation -> firestation.getAddress().equals(address));
        
        if (removed) {
            replaceEntities(allFirestations);
            saveToFile();
            logger.info("Firestation deleted successfully: {}", address);
        } else {
            logger.warn("Firestation not found for deletion: {}", address);
        }
        
        return removed;
    }
    
    /**
     * Check if a firestation exists at a specific address.
     * @param address the address to check
     * @return true if firestation exists, false otherwise
     */
    public boolean existsByAddress(String address) {
        logger.debug("Checking if firestation exists for address: {}", address);
        return findByAddress(address).isPresent();
    }
}