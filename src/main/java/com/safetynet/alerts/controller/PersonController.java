package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        log.info("GET /persons");
        List<Person> result = personService.getAllPersons();
        log.info("Returning persons: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String firstName, @PathVariable String lastName) {
        log.info("GET /persons/name/{}/{}", firstName, lastName);
        Person result = personService.getPerson(firstName, lastName);
        if (result == null) {
            log.info("No person found: {} {}. Returning empty JSON.", firstName, lastName);
            return ResponseEntity.ok(new Person());
        }
        log.info("Returning person: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        log.info("POST /persons");
        Person result = personService.addPerson(person);
        log.info("Returning added person: {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<Person> updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody Person person) {
        log.info("PUT /persons/name/{}/{}", firstName, lastName);
        Person result = personService.updatePerson(firstName, lastName, person);
        log.info("Returning updated person: {}", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/name/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        log.info("DELETE /persons/name/{}/{}", firstName, lastName);
        personService.deletePerson(firstName, lastName);
        log.info("Person deleted: {} {}", firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
