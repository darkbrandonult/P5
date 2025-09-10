package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordServiceTest {
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {
        medicalRecordService = new MedicalRecordService();
        medicalRecordService.getAllMedicalRecords().clear();
    }

    @Test
    void addAndGetMedicalRecord() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        MedicalRecord found = medicalRecordService.getMedicalRecord("John", "Doe");
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    void getAllMedicalRecords_emptyInitially() {
        assertTrue(medicalRecordService.getAllMedicalRecords().isEmpty());
    }

    @Test
    void getMedicalRecord_notFound() {
        assertNull(medicalRecordService.getMedicalRecord("Foo", "Bar"));
    }

    @Test
    void updateMedicalRecord_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        MedicalRecord updated = new MedicalRecord(); updated.setFirstName("John"); updated.setLastName("Doe"); updated.setBirthdate("01/01/2000");
        medicalRecordService.updateMedicalRecord("John", "Doe", updated);
        MedicalRecord found = medicalRecordService.getMedicalRecord("John", "Doe");
        assertEquals("01/01/2000", found.getBirthdate());
    }

    @Test
    void deleteMedicalRecord_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        medicalRecordService.deleteMedicalRecord("John", "Doe");
        assertNull(medicalRecordService.getMedicalRecord("John", "Doe"));
    }
}
