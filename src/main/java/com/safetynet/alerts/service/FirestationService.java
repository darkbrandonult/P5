package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FirestationService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    private final List<Firestation> firestations = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("./data.json");
            JsonNode root = mapper.readTree(file);
            JsonNode firestationsNode = root.get("firestations");
            if (firestationsNode != null && firestationsNode.isArray()) {
                for (JsonNode node : firestationsNode) {
                    Firestation firestation = mapper.treeToValue(node, Firestation.class);
                    firestations.add(firestation);
                }
            }
            logger.info("Loaded {} firestations from data.json", firestations.size());
        } catch (Exception e) {
            logger.error("Failed to load firestations from data.json", e);
        }
    }

    public List<Firestation> getAllFirestations() {
        logger.info("Fetching all firestations");
        return new ArrayList<>(firestations);
    }

    public Firestation addFirestation(Firestation firestation) {
        logger.info("Adding firestation: {}", firestation.getAddress());
        firestations.add(firestation);
        return firestation;
    }

    public Firestation updateFirestation(Firestation firestation) {
        logger.info("Updating firestation: {}", firestation.getAddress());
        for (int i = 0; i < firestations.size(); i++) {
            Firestation f = firestations.get(i);
            if (f.getAddress().equals(firestation.getAddress())) {
                firestations.set(i, firestation);
                return firestation;
            }
        }
        logger.warn("Firestation not found: {}", firestation.getAddress());
        return null;
    }

    public void deleteFirestation(String address) {
        logger.info("Deleting firestation: {}", address);
        Iterator<Firestation> iterator = firestations.iterator();
        while (iterator.hasNext()) {
            Firestation f = iterator.next();
            if (f.getAddress().equals(address)) {
                iterator.remove();
                logger.info("Firestation deleted: {}", address);
                return;
            }
        }
        logger.warn("Firestation not found for deletion: {}", address);
    }

    public Firestation getFirestationByAddress(String address) {
        for (Firestation f : firestations) {
            if (f.getAddress().equals(address)) {
                return f;
            }
        }
        logger.warn("Firestation not found: {}", address);
        return null;
    }

    public Firestation updateFirestation(String address, Firestation firestation) {
        for (int i = 0; i < firestations.size(); i++) {
            Firestation f = firestations.get(i);
            if (f.getAddress().equals(address)) {
                firestations.set(i, firestation);
                logger.info("Firestation updated: {}", address);
                return firestation;
            }
        }
        logger.warn("Firestation not found for update: {}", address);
        return null;
    }
}
