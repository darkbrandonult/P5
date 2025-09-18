package com.safetynet.alerts.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;

@ExtendWith(MockitoExtension.class)
class FirestationControllerUnitTest {
    @Mock
    private FirestationService firestationService;
    @InjectMocks
    private FirestationController firestationController;


    @Test
    void getAllFirestations_returnsOk() {
        List<Firestation> firestations = new ArrayList<>();
        when(firestationService.getAllFirestations()).thenReturn(firestations);
        ResponseEntity<List<Firestation>> response = firestationController.getAllFirestations();
        assertEquals(firestations, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getFirestationByAddress_returnsOk() {
        Firestation firestation = new Firestation();
        firestation.setAddress("1509 Culver St");
        firestation.setStation("3");
        when(firestationService.getFirestationByAddress("1509 Culver St")).thenReturn(firestation);
        ResponseEntity<Firestation> response = firestationController.getFirestationByAddress("1509 Culver St");
        assertEquals(firestation, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void addFirestation_returnsOk() {
        Firestation firestation = new Firestation();
        firestation.setAddress("1509 Culver St");
        firestation.setStation("3");
        when(firestationService.addFirestation(any(Firestation.class))).thenReturn(firestation);
        ResponseEntity<Firestation> response = firestationController.addFirestation(firestation);
        assertEquals(firestation, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void updateFirestation_returnsOk() {
        Firestation firestation = new Firestation();
        firestation.setAddress("1509 Culver St");
        firestation.setStation("3");
        when(firestationService.updateFirestation(any(Firestation.class))).thenReturn(firestation);
        ResponseEntity<Firestation> response = firestationController.updateFirestation("1509 Culver St", firestation);
        assertEquals(firestation, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void deleteFirestation_returnsOk() {
        doNothing().when(firestationService).deleteFirestation("1509 Culver St");
        ResponseEntity<Void> response = firestationController.deleteFirestation("1509 Culver St");
        assertEquals(200, response.getStatusCodeValue());
    }
}
