package com.safetynet.alerts.service;


import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

@Service
public class AlertService {

    @Autowired
    private FirestationService firestationService;
    @Autowired
    private PersonService personService;
    @Autowired
    private MedicalRecordService medicalRecordService;

    public FirestationCoverageDTO getFirestationCoverage(String stationNumber) {
        // 1. Get all addresses for the given station number
        List<Firestation> firestations = firestationService.getAllFirestations();
        Set<String> addresses = new HashSet<>();
        for (Firestation f : firestations) {
            if (f.getStation().equals(stationNumber)) {
                addresses.add(f.getAddress());
            }
        }

        // 2. Get all persons living at those addresses
        List<Person> allPersons = personService.getAllPersons();
        List<Person> coveredPersons = new ArrayList<>();
        int childCount = 0;
        int adultCount = 0;
        for (Person p : allPersons) {
            if (addresses.contains(p.getAddress())) {
                coveredPersons.add(p);
                // 3. Determine if child or adult
                MedicalRecord mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                if (mr != null) {
                    int age = calculateAge(mr.getBirthdate());
                    if (age <= 18) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                }
            }
        }
        return new FirestationCoverageDTO(coveredPersons, childCount, adultCount);
    }

    private int calculateAge(String birthdate) {
        if (birthdate == null || birthdate.isEmpty()) return 0;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate birth = LocalDate.parse(birthdate, formatter);
            return Period.between(birth, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }

    public ChildAlertDTO getChildAlert(String address) {
        List<Person> children = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        for (Person p : personService.getAllPersons()) {
            if (address.equals(p.getAddress())) {
                MedicalRecord mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                int age = mr != null ? calculateAge(mr.getBirthdate()) : 0;
                if (age <= 18) children.add(p);
                else adults.add(p);
            }
        }
        return new ChildAlertDTO(children, adults);
    }

    public List<PhoneAlertDTO> getPhoneAlert(String firestation) {
        Set<String> addresses = new HashSet<>();
        for (Firestation f : firestationService.getAllFirestations()) {
            if (firestation.equals(f.getStation())) addresses.add(f.getAddress());
        }
        List<PhoneAlertDTO> result = new ArrayList<>();
        for (Person p : personService.getAllPersons()) {
            if (addresses.contains(p.getAddress()) && p.getPhone() != null) {
                result.add(new PhoneAlertDTO(p.getPhone()));
            }
        }
        return result;
    }

    public FireDTO getFireInfo(String address) {
        String stationNumber = null;
        for (Firestation f : firestationService.getAllFirestations()) {
            if (address.equals(f.getAddress())) { stationNumber = f.getStation(); break; }
        }
        List<FireDTO.ResidentInfo> residents = new ArrayList<>();
        for (Person p : personService.getAllPersons()) {
            if (address.equals(p.getAddress())) {
                MedicalRecord mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                int age = mr != null ? calculateAge(mr.getBirthdate()) : 0;
                List<String> meds = mr != null ? mr.getMedications() : Collections.emptyList();
                List<String> allergies = mr != null ? mr.getAllergies() : Collections.emptyList();
                residents.add(new FireDTO.ResidentInfo(p.getFirstName(), p.getLastName(), p.getPhone(), age, meds, allergies));
            }
        }
        return new FireDTO(stationNumber, residents);
    }

    public Map<String, List<FloodDTO>> getFloodStations(List<String> stations) {
        Set<String> stationSet = new HashSet<>(stations);
        Set<String> addresses = new HashSet<>();
        for (Firestation f : firestationService.getAllFirestations()) {
            if (stationSet.contains(f.getStation())) addresses.add(f.getAddress());
        }
        Map<String, List<FloodDTO>> result = new HashMap<>();
        for (Person p : personService.getAllPersons()) {
            if (addresses.contains(p.getAddress())) {
                MedicalRecord mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                int age = mr != null ? calculateAge(mr.getBirthdate()) : 0;
                List<String> meds = mr != null ? mr.getMedications() : Collections.emptyList();
                List<String> allergies = mr != null ? mr.getAllergies() : Collections.emptyList();
                result.computeIfAbsent(p.getAddress(), k -> new ArrayList<>())
                      .add(new FloodDTO(p.getFirstName(), p.getLastName(), p.getPhone(), age, meds, allergies));
            }
        }
        return result;
    }

    public List<PersonInfoDTO> getPersonInfo(String lastName) {
        List<PersonInfoDTO> result = new ArrayList<>();
        for (Person p : personService.getAllPersons()) {
            if (lastName.equals(p.getLastName())) {
                MedicalRecord mr = medicalRecordService.getMedicalRecord(p.getFirstName(), p.getLastName());
                int age = mr != null ? calculateAge(mr.getBirthdate()) : 0;
                List<String> meds = mr != null ? mr.getMedications() : Collections.emptyList();
                List<String> allergies = mr != null ? mr.getAllergies() : Collections.emptyList();
                result.add(new PersonInfoDTO(p.getFirstName(), p.getLastName(), p.getAddress(), age, p.getEmail(), meds, allergies));
            }
        }
        return result;
    }
    public List<String> getCommunityEmail(String city) {
        List<String> emails = new ArrayList<>();
        for (Person p : personService.getAllPersons()) {
            if (city.equals(p.getCity()) && p.getEmail() != null) {
                emails.add(p.getEmail());
            }
        }
        return emails;
    }
}
