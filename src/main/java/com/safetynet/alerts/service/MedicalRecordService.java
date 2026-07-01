package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles business logic for MedicalRecord resources.
 * Delegates all data access and persistence to DataRepository.
 */
@Service
public class MedicalRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    private final DataRepository dataRepository;

    public MedicalRecordService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return new ArrayList<>(dataRepository.getMedicalRecords());
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        return dataRepository.getMedicalRecords().stream()
                .filter(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) {
        logger.info("Adding medical record: {} {}", record.getFirstName(), record.getLastName());
        dataRepository.getMedicalRecords().add(record);
        dataRepository.save();
        return record;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord updated) {
        List<MedicalRecord> records = dataRepository.getMedicalRecords();
        for (int i = 0; i < records.size(); i++) {
            MedicalRecord m = records.get(i);
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                records.set(i, updated);
                logger.info("Medical record updated: {} {}", firstName, lastName);
                dataRepository.save();
                return updated;
            }
        }
        logger.warn("Medical record not found for update: {} {}", firstName, lastName);
        return null;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord updated) {
        return updateMedicalRecord(updated.getFirstName(), updated.getLastName(), updated);
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        boolean removed = dataRepository.getMedicalRecords().removeIf(
                m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName));
        if (removed) {
            logger.info("Medical record deleted: {} {}", firstName, lastName);
            dataRepository.save();
        } else {
            logger.warn("Medical record not found for deletion: {} {}", firstName, lastName);
        }
    }
}
