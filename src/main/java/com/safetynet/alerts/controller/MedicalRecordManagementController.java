package com.safetynet.alerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordManagementController {
    private static final Logger log = LoggerFactory.getLogger(MedicalRecordManagementController.class);
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordManagementController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("POST /medicalRecord");
        MedicalRecord result = medicalRecordService.addMedicalRecord(medicalRecord);
        log.info("Returning added medical record: {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.info("PUT /medicalRecord");
        // For update, we need firstName and lastName from the body
        MedicalRecord result = medicalRecordService.updateMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName(), medicalRecord);
        log.info("Returning updated medical record: {}", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /medicalRecord?firstName={}&lastName={}", firstName, lastName);
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        log.info("Medical record deleted: {} {}", firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
