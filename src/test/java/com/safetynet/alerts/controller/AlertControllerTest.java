package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertControllerTest {
    @Mock private AlertService alertService;
    @InjectMocks private AlertController alertController;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getFirestationCoverage_returnsDTO() {
        FirestationCoverageDTO dto = new FirestationCoverageDTO(List.of(), 0, 1);
        when(alertService.getFirestationCoverage("1")).thenReturn(dto);
        ResponseEntity<FirestationCoverageDTO> response = alertController.getFirestationCoverage("1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getChildAlert_returnsDTO() {
        ChildAlertDTO dto = new ChildAlertDTO(List.of(), List.of());
        when(alertService.getChildAlert("123 St")).thenReturn(dto);
        ResponseEntity<ChildAlertDTO> response = alertController.getChildAlert("123 St");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getPhoneAlert_returnsList() {
        List<PhoneAlertDTO> list = List.of(new PhoneAlertDTO("555-1234"));
        when(alertService.getPhoneAlert("1")).thenReturn(list);
        ResponseEntity<List<PhoneAlertDTO>> response = alertController.getPhoneAlert("1");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getFireInfo_returnsDTO() {
        FireDTO dto = new FireDTO("1", List.of());
        when(alertService.getFireInfo("123 St")).thenReturn(dto);
        ResponseEntity<FireDTO> response = alertController.getFireInfo("123 St");
        assertEquals("1", response.getBody().getStationNumber());
    }

    @Test
    void getFloodStations_returnsMap() {
        Map<String, List<FloodDTO>> map = Map.of("123 St", List.of());
        when(alertService.getFloodStations(List.of("1"))).thenReturn(map);
        ResponseEntity<Map<String, List<FloodDTO>>> response = alertController.getFloodStations(List.of("1"));
        assertTrue(response.getBody().containsKey("123 St"));
    }

    @Test
    void getPersonInfo_returnsList() {
        List<PersonInfoDTO> list = List.of(new PersonInfoDTO("John", "Doe", "123 St", 40, "a@b.com", List.of(), List.of()));
        when(alertService.getPersonInfo("Doe")).thenReturn(list);
        ResponseEntity<List<PersonInfoDTO>> response = alertController.getPersonInfo("Doe");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getCommunityEmail_returnsList() {
        when(alertService.getCommunityEmail("Culver")).thenReturn(List.of("a@b.com"));
        ResponseEntity<List<String>> response = alertController.getCommunityEmail("Culver");
        assertEquals(1, response.getBody().size());
    }
}
