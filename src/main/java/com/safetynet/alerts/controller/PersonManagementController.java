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

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonManagementController {
    private static final Logger log = LoggerFactory.getLogger(PersonManagementController.class);
    private final PersonService personService;

    public PersonManagementController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        log.info("POST /person");
        Person result = personService.addPerson(person);
        log.info("Returning added person: {}", result);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        log.info("PUT /person");
        // For update, we need firstName and lastName from the body
        Person result = personService.updatePerson(person.getFirstName(), person.getLastName(), person);
        log.info("Returning updated person: {}", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("DELETE /person?firstName={}&lastName={}", firstName, lastName);
        personService.deletePerson(firstName, lastName);
        log.info("Person deleted: {} {}", firstName, lastName);
        return ResponseEntity.ok().build();
    }
}
