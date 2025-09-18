package com.safetynet.alerts.repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Base class for JSON file data repositories.
 * Provides common functionality for reading from and writing to the data.json file.
 */
@Component
public abstract class JsonFileDataRepository<T> {
    private static final Logger logger = LoggerFactory.getLogger(JsonFileDataRepository.class);
    private static final String DATA_FILE = "./data.json";
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<T> entities = new ArrayList<>();

    /**
     * Returns the JSON node name for this entity type (e.g., "persons", "firestations", "medicalrecords")
     */
    protected abstract String getJsonNodeName();

    /**
     * Returns the entity class for JSON mapping
     */
    protected abstract Class<T> getEntityClass();

    @PostConstruct
    public void init() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                logger.warn("Data file {} does not exist, starting with empty data", DATA_FILE);
                return;
            }

            JsonNode root = mapper.readTree(file);
            JsonNode entityNode = root.get(getJsonNodeName());
            if (entityNode != null && entityNode.isArray()) {
                entities.clear();
                for (JsonNode node : entityNode) {
                    T entity = mapper.treeToValue(node, getEntityClass());
                    entities.add(entity);
                }
            }
            logger.info("Loaded {} {} from {}", entities.size(), getJsonNodeName(), DATA_FILE);
        } catch (Exception e) {
            logger.error("Failed to load {} from {}", getJsonNodeName(), DATA_FILE, e);
        }
    }

    /**
     * Returns a copy of all entities
     */
    protected List<T> getAllEntities() {
        return new ArrayList<>(entities);
    }

    /**
     * Adds an entity to the collection
     */
    protected void addEntity(T entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the collection
     */
    protected boolean removeEntity(T entity) {
        return entities.remove(entity);
    }

    /**
     * Clears all entities and adds the new list
     */
    protected void replaceEntities(List<T> newEntities) {
        entities.clear();
        entities.addAll(newEntities);
    }

    /**
     * Saves the current entities to the JSON file
     */
    protected void saveToFile() {
        try {
            File file = new File(DATA_FILE);
            JsonNode root;
            
            if (file.exists()) {
                // Read existing data to preserve other sections
                root = mapper.readTree(file);
            } else {
                // Create new root object if file doesn't exist
                root = mapper.createObjectNode();
            }
            
            // Update only this entity's section
            if (root instanceof ObjectNode) {
                ArrayNode entityArray = mapper.valueToTree(entities);
                ((ObjectNode) root).set(getJsonNodeName(), entityArray);
            }
            
            // Write back to file with pretty printing
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
            logger.info("Saved {} {} to {}", entities.size(), getJsonNodeName(), DATA_FILE);
        } catch (Exception e) {
            logger.error("Failed to save {} to {}", getJsonNodeName(), DATA_FILE, e);
            throw new RuntimeException("Failed to save data to file", e);
        }
    }
}