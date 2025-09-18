package com.safetynet.alerts.service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {
    
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    
    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private MedicalRecord testRecord;

    @BeforeEach
    void setUp() {
        testRecord = new MedicalRecord();
        testRecord.setFirstName("John");
        testRecord.setLastName("Doe");
        testRecord.setBirthdate("01/01/1990");
    }

    @Test
    void addMedicalRecord_ShouldReturnSavedRecord() {
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(testRecord);
        
        MedicalRecord result = medicalRecordService.addMedicalRecord(testRecord);
        
        assertEquals(testRecord, result);
        verify(medicalRecordRepository, times(1)).save(testRecord);
    }

    @Test
    void getAllMedicalRecords_ShouldReturnAllRecords() {
        List<MedicalRecord> records = List.of(testRecord);
        when(medicalRecordRepository.findAll()).thenReturn(records);
        
        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();
        
        assertEquals(records, result);
        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    void getMedicalRecord_WhenRecordExists_ShouldReturnRecord() {
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe"))
            .thenReturn(Optional.of(testRecord));
        
        MedicalRecord result = medicalRecordService.getMedicalRecord("John", "Doe");
        
        assertEquals(testRecord, result);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void getMedicalRecord_WhenRecordDoesNotExist_ShouldReturnNull() {
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe"))
            .thenReturn(Optional.empty());
        
        MedicalRecord result = medicalRecordService.getMedicalRecord("John", "Doe");
        
        assertNull(result);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void updateMedicalRecord_WhenRecordExists_ShouldReturnUpdatedRecord() {
        when(medicalRecordRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(true);
        when(medicalRecordRepository.update(any(MedicalRecord.class))).thenReturn(testRecord);
        
        MedicalRecord result = medicalRecordService.updateMedicalRecord("John", "Doe", testRecord);
        
        assertEquals(testRecord, result);
        verify(medicalRecordRepository, times(1)).existsByFirstNameAndLastName("John", "Doe");
        verify(medicalRecordRepository, times(1)).update(testRecord);
    }

    @Test
    void updateMedicalRecord_WhenRecordDoesNotExist_ShouldReturnNull() {
        when(medicalRecordRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(false);
        
        MedicalRecord result = medicalRecordService.updateMedicalRecord("John", "Doe", testRecord);
        
        assertNull(result);
        verify(medicalRecordRepository, times(1)).existsByFirstNameAndLastName("John", "Doe");
        verify(medicalRecordRepository, never()).update(any(MedicalRecord.class));
    }

    @Test
    void deleteMedicalRecord_ShouldCallRepositoryDelete() {
        when(medicalRecordRepository.deleteByFirstNameAndLastName("John", "Doe")).thenReturn(true);
        
        medicalRecordService.deleteMedicalRecord("John", "Doe");
        
        verify(medicalRecordRepository, times(1)).deleteByFirstNameAndLastName("John", "Doe");
    }
}
