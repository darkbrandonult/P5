package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;

@Service
public class FirestationService implements FirestationServiceInterface {
    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    
    private final FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    @Override
    public List<Firestation> getAllFirestations() {
        logger.info("Fetching all firestations");
        return firestationRepository.findAll();
    }

    @Override
    public Firestation addFirestation(Firestation firestation) {
        logger.info("Adding firestation: {}", firestation.getAddress());
        return firestationRepository.save(firestation);
    }

    @Override
    public Firestation updateFirestation(Firestation firestation) {
        logger.info("Updating firestation: {}", firestation.getAddress());
        
        if (firestationRepository.existsByAddress(firestation.getAddress())) {
            return firestationRepository.update(firestation);
        } else {
            logger.warn("Firestation not found for update: {}", firestation.getAddress());
            return null;
        }
    }

    @Override
    public void deleteFirestation(String address) {
        logger.info("Deleting firestation: {}", address);
        boolean deleted = firestationRepository.deleteByAddress(address);
        if (!deleted) {
            logger.warn("Firestation not found for deletion: {}", address);
        }
    }

    @Override
    public Firestation getFirestationByAddress(String address) {
        logger.info("Fetching firestation by address: {}", address);
        Optional<Firestation> firestation = firestationRepository.findByAddress(address);
        if (firestation.isPresent()) {
            return firestation.get();
        }
        logger.warn("Firestation not found: {}", address);
        return null;
    }
}
