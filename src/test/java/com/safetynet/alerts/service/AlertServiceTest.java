package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertServiceTest {
    @Mock private FirestationService firestationService;
    @Mock private PersonService personService;
    @Mock private MedicalRecordService medicalRecordService;
    @InjectMocks private AlertService alertService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    private Person p(String fn, String ln, String addr, String phone, String city, String email) {
        Person p = new Person(); p.setFirstName(fn); p.setLastName(ln);
        p.setAddress(addr); p.setPhone(phone); p.setCity(city); p.setEmail(email); return p;
    }
    private Firestation fs(String addr, String station) {
        Firestation f = new Firestation(); f.setAddress(addr); f.setStation(station); return f;
    }
    private MedicalRecord mr(String fn, String ln, String bd, List<String> meds, List<String> allergies) {
        MedicalRecord m = new MedicalRecord(); m.setFirstName(fn); m.setLastName(ln);
        m.setBirthdate(bd); m.setMedications(meds); m.setAllergies(allergies); return m;
    }

    @Test
    void getFirestationCoverage_countsAdultsAndChildren() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1")));
        when(personService.getAllPersons()).thenReturn(List.of(
            p("John", "Adult", "123 St", "555", "City", "a@b.com"),
            p("Jane", "Child", "123 St", "556", "City", "b@b.com")
        ));
        when(medicalRecordService.getMedicalRecord("John", "Adult")).thenReturn(mr("John", "Adult", "01/01/1980", List.of(), List.of()));
        when(medicalRecordService.getMedicalRecord("Jane", "Child")).thenReturn(mr("Jane", "Child", "01/01/2015", List.of(), List.of()));
        FirestationCoverageDTO result = alertService.getFirestationCoverage("1");
        assertEquals(2, result.getPersons().size());
        assertEquals(1, result.getChildCount());
        assertEquals(1, result.getAdultCount());
    }

    @Test
    void getFirestationCoverage_noMatchingStation() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "2")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        FirestationCoverageDTO result = alertService.getFirestationCoverage("1");
        assertTrue(result.getPersons().isEmpty());
    }

    @Test
    void getFirestationCoverage_noMedicalRecord() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(null);
        FirestationCoverageDTO result = alertService.getFirestationCoverage("1");
        assertEquals(1, result.getPersons().size());
    }

    @Test
    void getChildAlert_separatesChildrenAndAdults() {
        when(personService.getAllPersons()).thenReturn(List.of(
            p("John", "Adult", "123 St", "555", "City", "a@b.com"),
            p("Jane", "Child", "123 St", "556", "City", "b@b.com")
        ));
        when(medicalRecordService.getMedicalRecord("John", "Adult")).thenReturn(mr("John", "Adult", "01/01/1980", List.of(), List.of()));
        when(medicalRecordService.getMedicalRecord("Jane", "Child")).thenReturn(mr("Jane", "Child", "01/01/2015", List.of(), List.of()));
        ChildAlertDTO result = alertService.getChildAlert("123 St");
        assertEquals(1, result.getChildren().size());
        assertEquals(1, result.getAdults().size());
    }

    @Test
    void getChildAlert_noMedicalRecord_countedAsChild() {
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(null);
        ChildAlertDTO result = alertService.getChildAlert("123 St");
        assertEquals(1, result.getChildren().size());
    }

    @Test
    void getChildAlert_differentAddress_excluded() {
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "456 Ave", "555", "City", "a@b.com")));
        ChildAlertDTO result = alertService.getChildAlert("123 St");
        assertTrue(result.getChildren().isEmpty());
        assertTrue(result.getAdults().isEmpty());
    }

    @Test
    void getPhoneAlert_returnsPhones() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555-1234", "City", "a@b.com")));
        List<PhoneAlertDTO> result = alertService.getPhoneAlert("1");
        assertEquals(1, result.size());
        assertEquals("555-1234", result.get(0).getPhone());
    }

    @Test
    void getPhoneAlert_noMatchingStation() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "2")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        assertTrue(alertService.getPhoneAlert("1").isEmpty());
    }

    @Test
    void getFireInfo_returnsResidents() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(
            mr("John", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1")));
        FireDTO result = alertService.getFireInfo("123 St");
        assertEquals("1", result.getStationNumber());
        assertEquals(1, result.getResidents().size());
    }

    @Test
    void getFireInfo_noMedicalRecord() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1")));
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(null);
        FireDTO result = alertService.getFireInfo("123 St");
        assertEquals(1, result.getResidents().size());
    }

    @Test
    void getFloodStations_groupsByAddress() {
        when(firestationService.getAllFirestations()).thenReturn(List.of(fs("123 St", "1"), fs("456 Ave", "2")));
        when(personService.getAllPersons()).thenReturn(List.of(
            p("John", "Doe", "123 St", "555", "City", "a@b.com"),
            p("Jane", "Smith", "456 Ave", "556", "City", "b@b.com")
        ));
        when(medicalRecordService.getMedicalRecord(anyString(), anyString())).thenReturn(null);
        Map<String, List<FloodDTO>> result = alertService.getFloodStations(List.of("1", "2"));
        assertTrue(result.containsKey("123 St"));
        assertTrue(result.containsKey("456 Ave"));
    }

    @Test
    void getPersonInfo_byLastName() {
        when(personService.getAllPersons()).thenReturn(List.of(
            p("John", "Doe", "123 St", "555", "City", "a@b.com"),
            p("Jane", "Smith", "456 Ave", "556", "City", "b@b.com")
        ));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(
            mr("John", "Doe", "01/01/1980", List.of(), List.of()));
        List<PersonInfoDTO> result = alertService.getPersonInfo("Doe");
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void getPersonInfo_noMedicalRecord() {
        when(personService.getAllPersons()).thenReturn(List.of(p("John", "Doe", "123 St", "555", "City", "a@b.com")));
        when(medicalRecordService.getMedicalRecord("John", "Doe")).thenReturn(null);
        List<PersonInfoDTO> result = alertService.getPersonInfo("Doe");
        assertEquals(1, result.size());
    }

    @Test
    void getCommunityEmail_byCity() {
        when(personService.getAllPersons()).thenReturn(List.of(
            p("John", "Doe", "123 St", "555", "Culver", "john@email.com"),
            p("Jane", "Smith", "456 Ave", "556", "Other", "jane@email.com")
        ));
        List<String> emails = alertService.getCommunityEmail("Culver");
        assertEquals(1, emails.size());
        assertEquals("john@email.com", emails.get(0));
    }

    @Test
    void getCommunityEmail_noEmail_excluded() {
        Person noEmail = p("John", "Doe", "123 St", "555", "Culver", null);
        when(personService.getAllPersons()).thenReturn(List.of(noEmail));
        assertTrue(alertService.getCommunityEmail("Culver").isEmpty());
    }
}
