package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FirestationCoverageDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

@Service
public class AlertService implements AlertServiceInterface {
    
    private final PersonServiceInterface personService;
    private final FirestationServiceInterface firestationService;
    private final MedicalRecordServiceInterface medicalRecordService;

    public AlertService(PersonServiceInterface personService, 
                       FirestationServiceInterface firestationService,
                       MedicalRecordServiceInterface medicalRecordService) {
        this.personService = personService;
        this.firestationService = firestationService;
        this.medicalRecordService = medicalRecordService;
    }

    // Helper method to calculate age from birthdate
    private int calculateAge(String birthdate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 25; // Default to adult if parsing fails
        }
    }

    @Override
    public ChildAlertDTO getChildAlert(String address) {
        List<Person> personsAtAddress = new ArrayList<>();
        List<Person> allPersons = personService.getAllPersons();
        
        // Find all persons at the specified address
        for (Person person : allPersons) {
            if (address.equals(person.getAddress())) {
                personsAtAddress.add(person);
            }
        }
        
        if (personsAtAddress.isEmpty()) {
            return new ChildAlertDTO(new ArrayList<>(), new ArrayList<>());
        }
        
        List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
        List<Person> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        
        // Categorize persons by age
        for (Person person : personsAtAddress) {
            MedicalRecord record = null;
            for (MedicalRecord mr : medicalRecords) {
                if (mr.getFirstName().equals(person.getFirstName()) && 
                    mr.getLastName().equals(person.getLastName())) {
                    record = mr;
                    break;
                }
            }
            
            int age = record != null ? calculateAge(record.getBirthdate()) : 25;
            if (age <= 18) {
                children.add(person);
            } else {
                adults.add(person);
            }
        }
        
        return new ChildAlertDTO(children, adults);
    }

    @Override
    public List<PhoneAlertDTO> getPhoneAlert(String firestation) {
        List<Firestation> firestations = firestationService.getAllFirestations();
        Set<String> addresses = new HashSet<>();
        
        for (Firestation f : firestations) {
            if (f.getStation() != null && f.getStation().equals(firestation)) {
                addresses.add(f.getAddress());
            }
        }
        
        List<Person> allPersons = personService.getAllPersons();
        Set<String> phones = new HashSet<>();
        
        for (Person p : allPersons) {
            if (addresses.contains(p.getAddress()) && p.getPhone() != null && !p.getPhone().isEmpty()) {
                phones.add(p.getPhone());
            }
        }
        
        List<PhoneAlertDTO> result = new ArrayList<>();
        for (String phone : phones) {
            result.add(new PhoneAlertDTO(phone));
        }
        return result;
    }

    @Override
    public FireDTO getFireInfo(String address) {
        // Get all persons at the address
        List<Person> allPersons = personService.getAllPersons();
        List<Person> personsAtAddress = new ArrayList<>();
        
        for (Person person : allPersons) {
            if (address.equals(person.getAddress())) {
                personsAtAddress.add(person);
            }
        }
        
        // Find the firestation number for this address
        List<Firestation> allFirestations = firestationService.getAllFirestations();
        String firestationNumber = null;
        for (Firestation firestation : allFirestations) {
            if (address.equals(firestation.getAddress())) {
                firestationNumber = firestation.getStation();
                break;
            }
        }
        
        // Get all medical records
        List<MedicalRecord> allRecords = medicalRecordService.getAllMedicalRecords();
        
        // Create FireDTO with residents information
        List<FireDTO.Resident> residents = new ArrayList<>();
        for (Person person : personsAtAddress) {
            // Calculate age
            int age = calculateAge(person.getFirstName() + " " + person.getLastName());
            
            // Find medical record
            MedicalRecord medicalRecord = null;
            for (MedicalRecord record : allRecords) {
                if (person.getFirstName().equals(record.getFirstName()) && 
                    person.getLastName().equals(record.getLastName())) {
                    medicalRecord = record;
                    break;
                }
            }
            
            // Create resident with medical information
            FireDTO.Resident resident = new FireDTO.Resident();
            resident.setFirstName(person.getFirstName());
            resident.setLastName(person.getLastName());
            resident.setPhone(person.getPhone());
            resident.setAge(age);
            
            if (medicalRecord != null) {
                resident.setMedications(medicalRecord.getMedications());
                resident.setAllergies(medicalRecord.getAllergies());
            }
            
            residents.add(resident);
        }
        
        FireDTO result = new FireDTO();
        result.setFirestationNumber(firestationNumber);
        result.setResidents(residents);
        return result;
    }

    @Override
    public Map<String, List<FloodDTO>> getFloodStations(List<String> stations) { 
        Map<String, List<FloodDTO>> result = new HashMap<>();
        
        // Get all firestations
        List<Firestation> allFirestations = firestationService.getAllFirestations();
        
        // Find all addresses covered by the requested stations
        Set<String> addresses = new HashSet<>();
        for (Firestation firestation : allFirestations) {
            if (firestation.getStation() != null && stations.contains(firestation.getStation())) {
                addresses.add(firestation.getAddress());
            }
        }
        
        // Get all persons and medical records
        List<Person> allPersons = personService.getAllPersons();
        List<MedicalRecord> allRecords = medicalRecordService.getAllMedicalRecords();
        
        // Group persons by address and create FloodDTO objects
        for (String address : addresses) {
            List<FloodDTO> personsAtAddress = new ArrayList<>();
            
            for (Person person : allPersons) {
                if (address.equals(person.getAddress())) {
                    // Find medical record for this person
                    MedicalRecord medicalRecord = null;
                    for (MedicalRecord record : allRecords) {
                        if (person.getFirstName().equals(record.getFirstName()) && 
                            person.getLastName().equals(record.getLastName())) {
                            medicalRecord = record;
                            break;
                        }
                    }
                    
                    // Calculate age and create FloodDTO
                    int age = 0;
                    List<String> medications = new ArrayList<>();
                    List<String> allergies = new ArrayList<>();
                    
                    if (medicalRecord != null) {
                        age = calculateAge(medicalRecord.getBirthdate());
                        medications = medicalRecord.getMedications() != null ? medicalRecord.getMedications() : new ArrayList<>();
                        allergies = medicalRecord.getAllergies() != null ? medicalRecord.getAllergies() : new ArrayList<>();
                    }
                    
                    FloodDTO floodDTO = new FloodDTO(
                        person.getFirstName(),
                        person.getLastName(),
                        age,
                        person.getPhone(),
                        medications,
                        allergies
                    );
                    personsAtAddress.add(floodDTO);
                }
            }
            
            if (!personsAtAddress.isEmpty()) {
                result.put(address, personsAtAddress);
            }
        }
        
        return result;
    }

    @Override
    public List<PersonInfoDTO> getPersonInfo(String lastName) { 
        List<PersonInfoDTO> result = new ArrayList<>();
        
        // Get all persons
        List<Person> allPersons = personService.getAllPersons();
        
        // Filter persons by lastName (case-insensitive)
        for (Person person : allPersons) {
            if (person.getLastName() != null && person.getLastName().equalsIgnoreCase(lastName)) {
                // Find medical record for this person
                MedicalRecord medicalRecord = null;
                List<MedicalRecord> allRecords = medicalRecordService.getAllMedicalRecords();
                for (MedicalRecord record : allRecords) {
                    if (person.getFirstName().equals(record.getFirstName()) && 
                        person.getLastName().equals(record.getLastName())) {
                        medicalRecord = record;
                        break;
                    }
                }
                
                // Calculate age and get medical info
                int age = 0;
                List<String> medications = new ArrayList<>();
                List<String> allergies = new ArrayList<>();
                
                if (medicalRecord != null) {
                    age = calculateAge(medicalRecord.getBirthdate());
                    medications = medicalRecord.getMedications() != null ? medicalRecord.getMedications() : new ArrayList<>();
                    allergies = medicalRecord.getAllergies() != null ? medicalRecord.getAllergies() : new ArrayList<>();
                }
                
                // Create PersonInfoDTO
                PersonInfoDTO personInfo = new PersonInfoDTO(
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    age,
                    person.getEmail(),
                    medications,
                    allergies
                );
                result.add(personInfo);
            }
        }
        
        return result;
    }

    @Override
    public List<String> getCommunityEmail(String city) { 
        Set<String> emails = new HashSet<>();
        
        // Get all persons
        List<Person> allPersons = personService.getAllPersons();
        
        // Filter persons by city (case-insensitive) and collect unique emails
        for (Person person : allPersons) {
            if (person.getCity() != null && person.getCity().equalsIgnoreCase(city)) {
                if (person.getEmail() != null && !person.getEmail().isEmpty()) {
                    emails.add(person.getEmail());
                }
            }
        }
        
        return new ArrayList<>(emails);
    }

    @Override
    public FirestationCoverageDTO getFirestationCoverage(String firestation) {
        List<Firestation> stations = firestationService.getAllFirestations();
        List<String> addresses = stations.stream()
                .filter(fs -> fs.getStation() != null && fs.getStation().equals(firestation))
                .map(Firestation::getAddress)
                .collect(Collectors.toList());

        List<Person> persons = personService.getAllPersons().stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList());

        int childCount = 0;
        int adultCount = 0;
        
        for (Person person : persons) {
            int age = calculateAge(person.getFirstName() + " " + person.getLastName());
            if (age <= 18) {
                childCount++;
            } else {
                adultCount++;
            }
        }

        return new FirestationCoverageDTO(persons, childCount, adultCount);
    }
}
