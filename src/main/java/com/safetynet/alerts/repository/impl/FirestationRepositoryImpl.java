package com.safetynet.alerts.repository.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.JsonFileDataRepository;

@Repository
public class FirestationRepositoryImpl extends JsonFileDataRepository<Firestation> implements FirestationRepository {
    private static final Logger logger = LoggerFactory.getLogger(FirestationRepositoryImpl.class);

    @Override
    protected String getJsonNodeName() {
        return "firestations";
    }

    @Override
    protected Class<Firestation> getEntityClass() {
        return Firestation.class;
    }

    @Override
    public List<Firestation> findAll() {
        logger.debug("Finding all firestations");
        return getAllEntities();
    }

    @Override
    public Optional<Firestation> findByAddress(String address) {
        logger.debug("Finding firestation by address: {}", address);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();
    }

    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        logger.debug("Finding firestations by station number: {}", stationNumber);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .toList();
    }

    @Override
    public List<String> findAddressesByStationNumber(String stationNumber) {
        logger.debug("Finding addresses by station number: {}", stationNumber);
        return getAllEntities().stream()
                .filter(firestation -> firestation.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .toList();
    }

    @Override
    public Firestation save(Firestation firestation) {
        logger.info("Saving firestation: address={}, station={}", firestation.getAddress(), firestation.getStation());
        
        // Check if firestation already exists for this address (update case)
        Optional<Firestation> existing = findByAddress(firestation.getAddress());
        if (existing.isPresent()) {
            return update(firestation);
        }
        
        // Add new firestation
        addEntity(firestation);
        saveToFile();
        return firestation;
    }

    @Override
    public Firestation update(Firestation firestation) {
        logger.info("Updating firestation: address={}, station={}", firestation.getAddress(), firestation.getStation());
        
        List<Firestation> allFirestations = getAllEntities();
        for (int i = 0; i < allFirestations.size(); i++) {
            Firestation existing = allFirestations.get(i);
            if (existing.getAddress().equals(firestation.getAddress())) {
                allFirestations.set(i, firestation);
                replaceEntities(allFirestations);
                saveToFile();
                return firestation;
            }
        }
        
        // If not found, treat as new
        logger.warn("Firestation not found for update, creating new: address={}", firestation.getAddress());
        return save(firestation);
    }

    @Override
    public boolean deleteByAddress(String address) {
        logger.info("Deleting firestation by address: {}", address);
        
        List<Firestation> allFirestations = getAllEntities();
        boolean removed = allFirestations.removeIf(firestation -> firestation.getAddress().equals(address));
        
        if (removed) {
            replaceEntities(allFirestations);
            saveToFile();
            logger.info("Firestation deleted successfully: {}", address);
        } else {
            logger.warn("Firestation not found for deletion: {}", address);
        }
        
        return removed;
    }

    @Override
    public boolean existsByAddress(String address) {
        logger.debug("Checking if firestation exists for address: {}", address);
        return findByAddress(address).isPresent();
    }
}