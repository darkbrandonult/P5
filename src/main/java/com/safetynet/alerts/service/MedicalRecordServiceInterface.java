package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import java.util.List;

/**
 * Service interface for MedicalRecord operations.
 * Defines the contract for business logic operations on MedicalRecord entities.
 */
public interface MedicalRecordServiceInterface {
    
    /**
     * Retrieve all medical records.
     * @return List of all medical records
     */
    List<MedicalRecord> getAllMedicalRecords();
    
    /**
     * Add a new medical record.
     * @param medicalRecord the medical record to add
     * @return the added medical record
     */
    MedicalRecord addMedicalRecord(MedicalRecord medicalRecord);
    
    /**
     * Get a specific medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return the medical record if found, null otherwise
     */
    MedicalRecord getMedicalRecord(String firstName, String lastName);
    
    /**
     * Update an existing medical record.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param medicalRecord the updated medical record information
     * @return the updated medical record
     */
    MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord);
    
    /**
     * Delete a medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     */
    void deleteMedicalRecord(String firstName, String lastName);
}