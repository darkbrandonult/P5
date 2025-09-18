package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import java.util.List;

/**
 * Service interface for Firestation operations.
 * Defines the contract for business logic operations on Firestation entities.
 */
public interface FirestationServiceInterface {
    
    /**
     * Retrieve all firestations.
     * @return List of all firestations
     */
    List<Firestation> getAllFirestations();
    
    /**
     * Add a new firestation.
     * @param firestation the firestation to add
     * @return the added firestation
     */
    Firestation addFirestation(Firestation firestation);
    
    /**
     * Update an existing firestation.
     * @param firestation the updated firestation information
     * @return the updated firestation
     */
    Firestation updateFirestation(Firestation firestation);
    
    /**
     * Delete a firestation by address.
     * @param address the address of the firestation to delete
     */
    void deleteFirestation(String address);
    
    /**
     * Get a firestation by address.
     * @param address the address to search for
     * @return the firestation if found, null otherwise
     */
    Firestation getFirestationByAddress(String address);
}