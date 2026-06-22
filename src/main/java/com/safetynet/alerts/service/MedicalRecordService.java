package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("./data.json");
            JsonNode root = mapper.readTree(file);
            JsonNode medicalRecordsNode = root.get("medicalrecords");
            if (medicalRecordsNode != null && medicalRecordsNode.isArray()) {
                for (JsonNode node : medicalRecordsNode) {
                    MedicalRecord medicalRecord = mapper.treeToValue(node, MedicalRecord.class);
                    medicalRecords.add(medicalRecord);
                }
            }
            logger.info("Loaded {} medical records from data.json", medicalRecords.size());
        } catch (Exception e) {
            logger.error("Failed to load medical records from data.json", e);
        }
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info("Fetching all medical records");
        return new ArrayList<>(medicalRecords);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Adding medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        for (MedicalRecord m : medicalRecords) {
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                return m;
            }
        }
        logger.warn("Medical record not found: {} {}", firstName, lastName);
        return null;
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
        logger.info("Updating medical record: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord m = medicalRecords.get(i);
            if (m.getFirstName().equals(medicalRecord.getFirstName()) && m.getLastName().equals(medicalRecord.getLastName())) {
                medicalRecords.set(i, medicalRecord);
                return medicalRecord;
            }
        }
        logger.warn("Medical record not found: {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return null;
    }

    public MedicalRecord updateMedicalRecord(String firstName, String lastName, MedicalRecord medicalRecord) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            MedicalRecord m = medicalRecords.get(i);
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                medicalRecords.set(i, medicalRecord);
                logger.info("Medical record updated: {} {}", firstName, lastName);
                return medicalRecord;
            }
        }
        logger.warn("Medical record not found for update: {} {}", firstName, lastName);
        return null;
    }

    public void deleteMedicalRecord(String firstName, String lastName) {
        logger.info("Deleting medical record: {} {}", firstName, lastName);
        Iterator<MedicalRecord> iterator = medicalRecords.iterator();
        while (iterator.hasNext()) {
            MedicalRecord m = iterator.next();
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                iterator.remove();
                logger.info("Medical record deleted: {} {}", firstName, lastName);
                return;
            }
        }
        logger.warn("Medical record not found for deletion: {} {}", firstName, lastName);
    }
}
