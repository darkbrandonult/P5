package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * Repository interface for MedicalRecord entity operations.
 * Defines the contract for data access operations on MedicalRecord objects.
 */
public interface MedicalRecordRepository {
    
    /**
     * Retrieve all medical records from the data source.
     * @return List of all medical records
     */
    List<MedicalRecord> findAll();
    
    /**
     * Find a medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return Optional containing the medical record if found, empty otherwise
     */
    Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Save a new medical record to the data source.
     * @param medicalRecord the medical record to save
     * @return the saved medical record
     */
    MedicalRecord save(MedicalRecord medicalRecord);
    
    /**
     * Update an existing medical record in the data source.
     * @param medicalRecord the medical record with updated information
     * @return the updated medical record
     */
    MedicalRecord update(MedicalRecord medicalRecord);
    
    /**
     * Delete a medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if deletion was successful, false otherwise
     */
    boolean deleteByFirstNameAndLastName(String firstName, String lastName);
    
    /**
     * Check if a medical record exists by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if medical record exists, false otherwise
     */
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}