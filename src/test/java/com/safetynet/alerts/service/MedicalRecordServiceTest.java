package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordServiceTest {
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void setUp() {
        medicalRecordService = new MedicalRecordService(new DataRepository());
    }

    @Test
    void addAndGetMedicalRecord() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        assertNotNull(medicalRecordService.getMedicalRecord("John", "Doe"));
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
    void updateMedicalRecord_byStrings_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        MedicalRecord updated = new MedicalRecord(); updated.setFirstName("John"); updated.setLastName("Doe"); updated.setBirthdate("01/01/2000");
        medicalRecordService.updateMedicalRecord("John", "Doe", updated);
        assertEquals("01/01/2000", medicalRecordService.getMedicalRecord("John", "Doe").getBirthdate());
    }

    @Test
    void updateMedicalRecord_byStrings_notFound() {
        MedicalRecord updated = new MedicalRecord(); updated.setFirstName("Foo"); updated.setLastName("Bar");
        assertNull(medicalRecordService.updateMedicalRecord("Foo", "Bar", updated));
    }

    @Test
    void updateMedicalRecord_byObject_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        MedicalRecord updated = new MedicalRecord(); updated.setFirstName("John"); updated.setLastName("Doe"); updated.setBirthdate("05/05/1995");
        assertNotNull(medicalRecordService.updateMedicalRecord(updated));
    }

    @Test
    void updateMedicalRecord_byObject_notFound() {
        MedicalRecord updated = new MedicalRecord(); updated.setFirstName("Foo"); updated.setLastName("Bar");
        assertNull(medicalRecordService.updateMedicalRecord(updated));
    }

    @Test
    void deleteMedicalRecord_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        medicalRecordService.addMedicalRecord(record);
        medicalRecordService.deleteMedicalRecord("John", "Doe");
        assertNull(medicalRecordService.getMedicalRecord("John", "Doe"));
    }

    @Test
    void deleteMedicalRecord_notFound_noError() {
        medicalRecordService.deleteMedicalRecord("Foo", "Bar");
        assertTrue(medicalRecordService.getAllMedicalRecords().isEmpty());
    }
}
