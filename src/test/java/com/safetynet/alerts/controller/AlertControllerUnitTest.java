
package com.safetynet.alerts.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FirestationCoverageDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.service.AlertService;

@ExtendWith(MockitoExtension.class)
class AlertControllerUnitTest {
    @Mock
    private AlertService alertService;
    @InjectMocks
    private AlertController alertController;

    @Test
    void getFirestationCoverage_returnsOk() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO();
        dto.setPersons(new ArrayList<>());
        dto.setAdultCount(2);
        dto.setChildCount(1);
        when(alertService.getFirestationCoverage("1")).thenReturn(dto);
        ResponseEntity<FirestationCoverageDTO> response = alertController.getFirestationCoverage("1");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getChildAlert_returnsOk() {
        ChildAlertDTO dto = new ChildAlertDTO();
        dto.setChildren(new ArrayList<>());
        dto.setAdults(new ArrayList<>());
        when(alertService.getChildAlert("1509 Culver St")).thenReturn(dto);
        ResponseEntity<ChildAlertDTO> response = alertController.getChildAlert("1509 Culver St");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getPhoneAlert_returnsOk() {
        List<PhoneAlertDTO> dto = new ArrayList<>();
        when(alertService.getPhoneAlert("2")).thenReturn(dto);
        ResponseEntity<List<PhoneAlertDTO>> response = alertController.getPhoneAlert("2");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getFireInfo_returnsOk() {
        FireDTO dto = new FireDTO();
        dto.setLastName("Boyd");
        dto.setAge(38);
        dto.setPhone("841-874-6512");
        dto.setMedications(new ArrayList<>());
        dto.setAllergies(new ArrayList<>());
        when(alertService.getFireInfo("1509 Culver St")).thenReturn(dto);
        ResponseEntity<FireDTO> response = alertController.getFireInfo("1509 Culver St");
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getFloodStations_returnsOk() {
        Map<String, List<FloodDTO>> dto = new HashMap<>();
        when(alertService.getFloodStations(anyList())).thenReturn(dto);
        ResponseEntity<Map<String, List<FloodDTO>>> response = alertController.getFloodStations(Arrays.asList("1", "2"));
        assertEquals(dto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}
