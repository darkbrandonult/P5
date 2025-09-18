package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/firestations")
public class FirestationController {
    private static final Logger log = LoggerFactory.getLogger(FirestationController.class);
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping
    public ResponseEntity<List<Firestation>> getAllFirestations() {
        log.info("GET /firestations");
        List<Firestation> result = firestationService.getAllFirestations();
        log.info("Returning firestations: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/address/{address}")
    public ResponseEntity<Firestation> getFirestationByAddress(@PathVariable String address) {
        log.info("GET /firestations/address/{}", address);
        Firestation result = firestationService.getFirestationByAddress(address);
        if (result == null) {
            log.info("No firestation found at address: {}. Returning empty JSON.", address);
            return ResponseEntity.ok(new Firestation());
        }
        log.info("Returning firestation: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {
        log.info("POST /firestations");
        Firestation result = firestationService.addFirestation(firestation);
        log.info("Returning added firestation: {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/address/{address}")
    public ResponseEntity<Firestation> updateFirestation(@PathVariable String address, @RequestBody Firestation firestation) {
        log.info("PUT /firestations/address/{}", address);
        
        // Ensure the firestation has the correct address
        firestation.setAddress(address);
        
        Firestation result = firestationService.updateFirestation(firestation);
        log.info("Returning updated firestation: {}", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/address/{address}")
    public ResponseEntity<Void> deleteFirestation(@PathVariable String address) {
        log.info("DELETE /firestations/address/{}", address);
        firestationService.deleteFirestation(address);
        log.info("Firestation deleted at address: {}", address);
        return ResponseEntity.ok().build();
    }
}
