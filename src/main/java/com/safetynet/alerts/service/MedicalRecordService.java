package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService implements MedicalRecordServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Fetching all medical records");
        return medicalRecordRepository.findAll();
    }

    @Override
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Adding medical record for: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        logger.info("Fetching medical record: {} {}", firstName, lastName);
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName);
        if (medicalRecord.isPresent()) {
            return medicalRecord.get();
        }
        logger.warn("Medical record not found: {} {}", firstName, lastName);
        return null;
    }

    @Override
    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        logger.info("Updating medical record: {} {}", firstName, lastName);
        
        // Ensure the medical record has the correct names
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        
        if (medicalRecordRepository.existsByFirstNameAndLastName(firstName, lastName)) {
            return medicalRecordRepository.update(medicalRecord);
        } else {
            logger.warn("Medical record not found for update: {} {}", firstName, lastName);
            return null;
        }
    }

    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Deleting medical record: {} {}", firstName, lastName);
        boolean deleted = medicalRecordRepository.deleteByFirstNameAndLastName(firstName, lastName);
        if (!deleted) {
            logger.warn("Medical record not found for deletion: {} {}", firstName, lastName);
        }
    }
}
