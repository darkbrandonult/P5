package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FirestationServiceTest {
    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
        firestationService = new FirestationService(new DataRepository());
    }

    private Firestation fs(String addr, String station) {
        Firestation f = new Firestation(); f.setAddress(addr); f.setStation(station); return f;
    }

    @Test
    void getAllFirestations_emptyInitially() {
        assertTrue(firestationService.getAllFirestations().isEmpty());
    }

    @Test
    void addAndGetFirestation() {
        firestationService.addFirestation(fs("123 Main St", "1"));
        Firestation found = firestationService.getFirestationByAddress("123 Main St");
        assertNotNull(found);
        assertEquals("1", found.getStation());
    }

    @Test
    void getFirestationByAddress_notFound() {
        assertNull(firestationService.getFirestationByAddress("Unknown"));
    }

    @Test
    void updateFirestation_byAddress_success() {
        firestationService.addFirestation(fs("123 Main St", "1"));
        Firestation result = firestationService.updateFirestation("123 Main St", fs("123 Main St", "2"));
        assertNotNull(result);
        assertEquals("2", firestationService.getFirestationByAddress("123 Main St").getStation());
    }

    @Test
    void updateFirestation_byAddress_notFound() {
        assertNull(firestationService.updateFirestation("Unknown", fs("Unknown", "2")));
    }

    @Test
    void updateFirestation_byObject_success() {
        firestationService.addFirestation(fs("456 Ave", "1"));
        Firestation result = firestationService.updateFirestation(fs("456 Ave", "3"));
        assertNotNull(result);
        assertEquals("3", result.getStation());
    }

    @Test
    void updateFirestation_byObject_notFound() {
        assertNull(firestationService.updateFirestation(fs("Unknown", "2")));
    }

    @Test
    void deleteFirestation_success() {
        firestationService.addFirestation(fs("123 Main St", "1"));
        firestationService.deleteFirestation("123 Main St");
        assertNull(firestationService.getFirestationByAddress("123 Main St"));
    }

    @Test
    void deleteFirestation_notFound_noError() {
        firestationService.deleteFirestation("Unknown");
        assertTrue(firestationService.getAllFirestations().isEmpty());
    }

    @Test
    void getAllFirestations_returnsAll() {
        firestationService.addFirestation(fs("A", "1"));
        firestationService.addFirestation(fs("B", "2"));
        assertEquals(2, firestationService.getAllFirestations().size());
    }
}
