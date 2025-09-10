package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Collections;

class MedicalRecordControllerTest {
    @Mock
    private MedicalRecordService medicalRecordService;
    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMedicalRecords_returnsList() {
        MedicalRecord r1 = new MedicalRecord(); r1.setFirstName("John"); r1.setLastName("Doe");
        MedicalRecord r2 = new MedicalRecord(); r2.setFirstName("Jane"); r2.setLastName("Smith");
        List<MedicalRecord> records = Arrays.asList(r1, r2);
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(records);
        ResponseEntity<List<MedicalRecord>> response = medicalRecordController.getAllMedicalRecords();
        assertNotNull(response.getBody());
        List<MedicalRecord> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        verify(medicalRecordService).getAllMedicalRecords();
    }

    @Test
    void getMedicalRecord_found() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(record);
        ResponseEntity<MedicalRecord> response = medicalRecordController.getMedicalRecord("John", "Doe");
        assertNotNull(response.getBody());
        MedicalRecord body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        assertEquals("Doe", body.getLastName());
    }

    @Test
    void getMedicalRecord_notFound_returnsEmpty() {
        when(medicalRecordService.getMedicalRecord("Foo", "Bar")).thenReturn(null);
        ResponseEntity<MedicalRecord> response = medicalRecordController.getMedicalRecord("Foo", "Bar");
        assertNotNull(response.getBody());
        MedicalRecord body = response.getBody();
        assertNotNull(body);
        assertTrue(body.getFirstName() == null || body.getFirstName().isEmpty());
    }

    @Test
    void addMedicalRecord_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        when(medicalRecordService.addMedicalRecord(record)).thenReturn(record);
        ResponseEntity<MedicalRecord> response = medicalRecordController.addMedicalRecord(record);
        assertNotNull(response.getBody());
        MedicalRecord body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        verify(medicalRecordService).addMedicalRecord(record);
    }

    @Test
    void updateMedicalRecord_success() {
        MedicalRecord record = new MedicalRecord(); record.setFirstName("John"); record.setLastName("Doe");
        when(medicalRecordService.updateMedicalRecord(eq("John"), eq("Doe"), any(MedicalRecord.class))).thenReturn(record);
        ResponseEntity<MedicalRecord> response = medicalRecordController.updateMedicalRecord("John", "Doe", record);
        assertNotNull(response.getBody());
        MedicalRecord body = response.getBody();
        assertNotNull(body);
        assertEquals("John", body.getFirstName());
        verify(medicalRecordService).updateMedicalRecord("John", "Doe", record);
    }

    @Test
    void deleteMedicalRecord_success() {
        doNothing().when(medicalRecordService).deleteMedicalRecord("John", "Doe");
        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord("John", "Doe");
        assertEquals(200, response.getStatusCodeValue());
        verify(medicalRecordService).deleteMedicalRecord("John", "Doe");
    }
}
