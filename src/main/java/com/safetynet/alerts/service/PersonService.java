package com.safetynet.alerts.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final List<Person> persons = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("./data.json");
            JsonNode root = mapper.readTree(file);
            JsonNode personsNode = root.get("persons");
            if (personsNode != null && personsNode.isArray()) {
                for (JsonNode node : personsNode) {
                    Person person = mapper.treeToValue(node, Person.class);
                    persons.add(person);
                }
            }
            logger.info("Loaded {} persons from data.json", persons.size());
        } catch (Exception e) {
            logger.error("Failed to load persons from data.json", e);
        }
    }

    public List<Person> getAllPersons() {
        logger.info("Fetching all persons");
        return new ArrayList<>(persons);
    }

    public Person addPerson(Person person) {
        logger.info("Adding person: {} {}", person.getFirstName(), person.getLastName());
        persons.add(person);
        saveToFile();
        return person;
    }

    public Person getPerson(String firstName, String lastName) {
        for (Person p : persons) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                return p;
            }
        }
        logger.warn("Person not found: {} {}", firstName, lastName);
        return null;
    }

    public Person updatePerson(String firstName, String lastName, Person person) {
        for (int i = 0; i < persons.size(); i++) {
            Person p = persons.get(i);
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                persons.set(i, person);
                logger.info("Person updated: {} {}", firstName, lastName);
                saveToFile();
                return person;
            }
        }
        logger.warn("Person not found for update: {} {}", firstName, lastName);
        return null;
    }

    public void deletePerson(String firstName, String lastName) {
        logger.info("Deleting person: {} {}", firstName, lastName);
        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person p = iterator.next();
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                iterator.remove();
                logger.info("Person deleted: {} {}", firstName, lastName);
                saveToFile();
                return;
            }
        }
        logger.warn("Person not found for deletion: {} {}", firstName, lastName);
    }

    void saveToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("./data.json");
            
            // Read existing data to preserve other sections (firestations, medicalRecords)
            JsonNode root = mapper.readTree(file);
            
            // Update only the persons section
            if (root instanceof com.fasterxml.jackson.databind.node.ObjectNode) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) root).set("persons", 
                    mapper.valueToTree(persons));
            }
            
            // Write back to file
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
            logger.info("Saved {} persons to data.json", persons.size());
        } catch (Exception e) {
            logger.error("Failed to save persons to data.json", e);
        }
    }
}
