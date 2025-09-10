package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {
    private static final Logger log = LoggerFactory.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        log.info("GET /medicalrecords");
        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();
        log.info("Returning medical records: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        log.info("GET /medicalrecords/name/{}/{}", firstName, lastName);
        MedicalRecord result = medicalRecordService.getMedicalRecord(firstName, lastName);
        if (result == null) {
            log.info("No medical record found: {} {}. Returning empty JSON.", firstName, lastName);
            return ResponseEntity.ok(new MedicalRecord());
        }
        log.info("Returning medical record: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("POST /medicalrecords");
        MedicalRecord result = medicalRecordService.addMedicalRecord(medicalRecord);
        log.info("Returning added medical record: {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecord medicalRecord) {
        log.info("PUT /medicalrecords/name/{}/{}", firstName, lastName);
        MedicalRecord result = medicalRecordService.updateMedicalRecord(firstName, lastName, medicalRecord);
        log.info("Returning updated medical record: {}", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        log.info("DELETE /medicalrecords/name/{}/{}", firstName, lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        log.info("Medical record deleted: {} {}", firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
