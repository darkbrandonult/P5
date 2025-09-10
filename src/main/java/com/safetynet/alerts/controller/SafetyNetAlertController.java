package com.safetynet.alerts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SafetyNetAlertController {
    @GetMapping("/")
    public String index() {
        return "SafetyNetAlert World!";
    }

    @GetMapping("/calc")
    public ResponseEntity<Map<String, Object>> calc(@RequestParam int left, @RequestParam int right) {
        Map<String, Object> result = new HashMap<>();
        result.put("left", left);
        result.put("right", right);
        result.put("answer", left + right);
        return ResponseEntity.ok(result);
    }
}
