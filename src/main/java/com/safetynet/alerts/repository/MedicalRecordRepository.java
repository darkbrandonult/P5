package com.safetynet.alerts.repository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;

/**
 * Concrete repository class for MedicalRecord entity operations.
 * Handles direct file operations for medical record data.
 */
@Repository
public class MedicalRecordRepository extends JsonFileDataRepository<MedicalRecord> {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

    @Override
    protected String getJsonNodeName() {
        return "medicalrecords";
    }

    @Override
    protected Class<MedicalRecord> getEntityClass() {
        return MedicalRecord.class;
    }

    /**
     * Retrieve all medical records from the data source.
     * @return List of all medical records
     */
    public List<MedicalRecord> findAll() {
        logger.debug("Finding all medical records");
        return getAllEntities();
    }
    
    /**
     * Find a medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return Optional containing the medical record if found, empty otherwise
     */
    public Optional<MedicalRecord> findByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Finding medical record by name: {} {}", firstName, lastName);
        return getAllEntities().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))
                .findFirst();
    }
    
    /**
     * Save a new medical record to the data source.
     * @param medicalRecord the medical record to save
     * @return the saved medical record
     */
    public MedicalRecord save(MedicalRecord medicalRecord) {
        logger.info("Saving medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        
        // Check if medical record already exists (update case)
        Optional<MedicalRecord> existing = findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
        if (existing.isPresent()) {
            return update(medicalRecord);
        }
        
        // Add new medical record
        addEntity(medicalRecord);
        saveToFile();
        return medicalRecord;
    }
    
    /**
     * Update an existing medical record in the data source.
     * @param medicalRecord the medical record with updated information
     * @return the updated medical record
     */
    public MedicalRecord update(MedicalRecord medicalRecord) {
        logger.info("Updating medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        
        List<MedicalRecord> allRecords = getAllEntities();
        for (int i = 0; i < allRecords.size(); i++) {
            MedicalRecord existing = allRecords.get(i);
            if (existing.getFirstName().equals(medicalRecord.getFirstName()) && 
                existing.getLastName().equals(medicalRecord.getLastName())) {
                allRecords.set(i, medicalRecord);
                replaceEntities(allRecords);
                saveToFile();
                return medicalRecord;
            }
        }
        
        // If not found, treat as new
        logger.warn("Medical record not found for update, creating new: {} {}", 
                   medicalRecord.getFirstName(), medicalRecord.getLastName());
        return save(medicalRecord);
    }
    
    /**
     * Delete a medical record by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteByFirstNameAndLastName(String firstName, String lastName) {
        logger.info("Deleting medical record: {} {}", firstName, lastName);
        
        List<MedicalRecord> allRecords = getAllEntities();
        boolean removed = allRecords.removeIf(medicalRecord -> 
            medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        
        if (removed) {
            replaceEntities(allRecords);
            saveToFile();
            logger.info("Medical record deleted successfully: {} {}", firstName, lastName);
        } else {
            logger.warn("Medical record not found for deletion: {} {}", firstName, lastName);
        }
        
        return removed;
    }
    
    /**
     * Check if a medical record exists by first name and last name.
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @return true if medical record exists, false otherwise
     */
    public boolean existsByFirstNameAndLastName(String firstName, String lastName) {
        logger.debug("Checking if medical record exists: {} {}", firstName, lastName);
        return findByFirstNameAndLastName(firstName, lastName).isPresent();
    }
}