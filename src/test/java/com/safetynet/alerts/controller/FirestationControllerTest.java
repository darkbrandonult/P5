package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
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
import static org.mockito.ArgumentMatchers.any;

class FirestationControllerTest {
    @Mock
    private FirestationService firestationService;
    @InjectMocks
    private FirestationController firestationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFirestations_returnsList() {
        Firestation f1 = new Firestation(); f1.setAddress("A");
        when(firestationService.getAllFirestations()).thenReturn(Arrays.asList(f1));
        ResponseEntity<List<Firestation>> response = firestationController.getAllFirestations();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getFirestationByAddress_found() {
        Firestation f = new Firestation(); f.setAddress("A");
        when(firestationService.getFirestationByAddress("A")).thenReturn(f);
        ResponseEntity<Firestation> response = firestationController.getFirestationByAddress("A");
        assertEquals("A", response.getBody().getAddress());
    }

    @Test
    void getFirestationByAddress_notFound() {
        when(firestationService.getFirestationByAddress("B")).thenReturn(null);
        ResponseEntity<Firestation> response = firestationController.getFirestationByAddress("B");
        assertNotNull(response.getBody());
    }

    @Test
    void addFirestation_works() {
        Firestation f = new Firestation();
        when(firestationService.addFirestation(f)).thenReturn(f);
        ResponseEntity<Firestation> response = firestationController.addFirestation(f);
        assertEquals(f, response.getBody());
    }

    @Test
    void updateFirestation_works() {
        Firestation f = new Firestation();
        f.setAddress("A");
        when(firestationService.updateFirestation(any(Firestation.class))).thenReturn(f);
        ResponseEntity<Firestation> response = firestationController.updateFirestation("A", f);
        assertEquals(f, response.getBody());
    }

    @Test
    void deleteFirestation_works() {
        doNothing().when(firestationService).deleteFirestation("A");
        ResponseEntity<Void> response = firestationController.deleteFirestation("A");
        assertEquals(200, response.getStatusCodeValue());
    }
}
