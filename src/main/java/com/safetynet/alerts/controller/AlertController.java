package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.AlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class AlertController {
    private static final Logger log = LoggerFactory.getLogger(AlertController.class);
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationCoverageDTO> getFirestationCoverage(@RequestParam String stationNumber) {
        log.info("GET /firestation?stationNumber={}", stationNumber);
        FirestationCoverageDTO dto = alertService.getFirestationCoverage(stationNumber);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildAlert(@RequestParam String address) {
        log.info("GET /childAlert?address={}", address);
        ChildAlertDTO dto = alertService.getChildAlert(address);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<PhoneAlertDTO>> getPhoneAlert(@RequestParam String firestation) {
        log.info("GET /phoneAlert?firestation={}", firestation);
        List<PhoneAlertDTO> dto = alertService.getPhoneAlert(firestation);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getFireInfo(@RequestParam String address) {
        log.info("GET /fire?address={}", address);
        FireDTO dto = alertService.getFireInfo(address);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Map<String, List<FloodDTO>>> getFloodStations(@RequestParam List<String> stations) {
        log.info("GET /flood/stations?stations={}", stations);
        Map<String, List<FloodDTO>> dto = alertService.getFloodStations(stations);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfo(@RequestParam String lastName) {
        log.info("GET /personInfo?lastName={}", lastName);
        List<PersonInfoDTO> dto = alertService.getPersonInfo(lastName);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam String city) {
        log.info("GET /communityEmail?city={}", city);
        List<String> dto = alertService.getCommunityEmail(city);
        log.info("Returning: {}", dto);
        return ResponseEntity.ok(dto);
    }
}
