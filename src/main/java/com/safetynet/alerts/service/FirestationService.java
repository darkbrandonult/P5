package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles business logic for Firestation resources.
 * Delegates all data access and persistence to DataRepository.
 */
@Service
public class FirestationService {

    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    private final DataRepository dataRepository;

    public FirestationService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<Firestation> getAllFirestations() {
        return new ArrayList<>(dataRepository.getFirestations());
    }

    public Firestation getFirestationByAddress(String address) {
        return dataRepository.getFirestations().stream()
                .filter(f -> f.getAddress().equals(address))
                .findFirst()
                .orElse(null);
    }

    public Firestation addFirestation(Firestation firestation) {
        logger.info("Adding firestation: {}", firestation.getAddress());
        dataRepository.getFirestations().add(firestation);
        dataRepository.save();
        return firestation;
    }

    public Firestation updateFirestation(String address, Firestation updated) {
        List<Firestation> firestations = dataRepository.getFirestations();
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i).getAddress().equals(address)) {
                firestations.set(i, updated);
                logger.info("Firestation updated at address: {}", address);
                dataRepository.save();
                return updated;
            }
        }
        logger.warn("Firestation not found for update: {}", address);
        return null;
    }

    public Firestation updateFirestation(Firestation updated) {
        return updateFirestation(updated.getAddress(), updated);
    }

    public void deleteFirestation(String address) {
        boolean removed = dataRepository.getFirestations().removeIf(
                f -> f.getAddress().equals(address));
        if (removed) {
            logger.info("Firestation deleted at address: {}", address);
            dataRepository.save();
        } else {
            logger.warn("Firestation not found for deletion: {}", address);
        }
    }
}
