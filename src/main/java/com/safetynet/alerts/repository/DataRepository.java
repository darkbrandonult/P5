package com.safetynet.alerts.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Single source of truth for all in-memory data.
 * Responsible for loading data.json on startup and persisting changes back to it.
 * No business logic lives here — only raw data access and file I/O.
 */
@Repository
public class DataRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataRepository.class);
    private static final String DATA_FILE = "./data.json";

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<Person> persons = new ArrayList<>();
    private final List<Firestation> firestations = new ArrayList<>();
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();

    @PostConstruct
    public void load() {
        try {
            File file = new File(DATA_FILE);
            JsonNode root = mapper.readTree(file);

            if (root.has("persons")) {
                for (JsonNode node : root.get("persons")) {
                    persons.add(mapper.treeToValue(node, Person.class));
                }
            }
            if (root.has("firestations")) {
                for (JsonNode node : root.get("firestations")) {
                    firestations.add(mapper.treeToValue(node, Firestation.class));
                }
            }
            if (root.has("medicalrecords")) {
                for (JsonNode node : root.get("medicalrecords")) {
                    medicalRecords.add(mapper.treeToValue(node, MedicalRecord.class));
                }
            }
            logger.info("Loaded {} persons, {} firestations, {} medical records from {}",
                    persons.size(), firestations.size(), medicalRecords.size(), DATA_FILE);
        } catch (Exception e) {
            logger.error("Failed to load data from {}", DATA_FILE, e);
        }
    }

    /**
     * Writes all data back to data.json atomically.
     * Called by services after any mutation.
     */
    public synchronized void save() {
        try {
            ObjectNode root = mapper.createObjectNode();
            root.set("persons", mapper.valueToTree(persons));
            root.set("firestations", mapper.valueToTree(firestations));
            root.set("medicalrecords", mapper.valueToTree(medicalRecords));
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE), root);
            logger.info("Data saved to {}", DATA_FILE);
        } catch (Exception e) {
            logger.error("Failed to save data to {}", DATA_FILE, e);
        }
    }

    public List<Person> getPersons() { return persons; }
    public List<Firestation> getFirestations() { return firestations; }
    public List<MedicalRecord> getMedicalRecords() { return medicalRecords; }
}
