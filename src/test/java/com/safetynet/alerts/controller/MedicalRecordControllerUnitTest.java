
package com.safetynet.alerts.controller;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq; // Keeping this import as it is used in setUp()
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerUnitTest {
    @Mock
    private MedicalRecordService medicalRecordService;
    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @Test
    void getAllMedicalRecords_returnsOk() {
        List<MedicalRecord> records = new ArrayList<>();
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(records);
        ResponseEntity<List<MedicalRecord>> response = medicalRecordController.getAllMedicalRecords();
        assertEquals(records, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getMedicalRecord_returnsOk() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("03/06/1984");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        when(medicalRecordService.getMedicalRecord(eq("John"), eq("Doe"))).thenReturn(medicalRecord);
        ResponseEntity<MedicalRecord> response = medicalRecordController.getMedicalRecord("John", "Doe");
        assertEquals(medicalRecord, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void addMedicalRecord_returnsOk() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("03/06/1984");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        when(medicalRecordService.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);
        ResponseEntity<MedicalRecord> response = medicalRecordController.addMedicalRecord(medicalRecord);
        assertEquals(medicalRecord, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateMedicalRecord_returnsOk() {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("03/06/1984");
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        when(medicalRecordService.updateMedicalRecord(eq("John"), eq("Doe"), any(MedicalRecord.class))).thenReturn(medicalRecord);
        ResponseEntity<MedicalRecord> response = medicalRecordController.updateMedicalRecord("John", "Doe", medicalRecord);
        assertEquals(medicalRecord, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteMedicalRecord_returnsOk() {
        doNothing().when(medicalRecordService).deleteMedicalRecord("John", "Doe");
        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord("John", "Doe");
        assertEquals(200, response.getStatusCodeValue());
    }
}
