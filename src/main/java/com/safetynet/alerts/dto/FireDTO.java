package com.safetynet.alerts.dto;

import java.util.List;
import java.util.Objects;

public class FireDTO {
    private String lastName;
    private int age;
    private String phone;
    private List<String> medications;
    private List<String> allergies;
    
    // Fields for complete fire response
    private String firestationNumber;
    private List<Resident> residents;
    
    // Nested class for residents
    public static class Resident {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;
        
        // Constructors
        public Resident() {}
        
        public Resident(String firstName, String lastName, String phone, int age, 
                       List<String> medications, List<String> allergies) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.age = age;
            this.medications = medications;
            this.allergies = allergies;
        }
        
        // Getters and setters
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        
        public List<String> getMedications() { return medications; }
        public void setMedications(List<String> medications) { this.medications = medications; }
        
        public List<String> getAllergies() { return allergies; }
        public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    }

    public FireDTO() {}

    public FireDTO(String lastName, int age, String phone, List<String> medications, List<String> allergies) {
        this.lastName = lastName;
        this.age = age;
        this.phone = phone;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<String> getMedications() { return medications; }
    public void setMedications(List<String> medications) { this.medications = medications; }
    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
    
    // Getters and setters for complete fire response
    public String getFirestationNumber() { return firestationNumber; }
    public void setFirestationNumber(String firestationNumber) { this.firestationNumber = firestationNumber; }
    
    public List<Resident> getResidents() { return residents; }
    public void setResidents(List<Resident> residents) { this.residents = residents; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireDTO fireDTO = (FireDTO) o;
        return age == fireDTO.age &&
                Objects.equals(lastName, fireDTO.lastName) &&
                Objects.equals(phone, fireDTO.phone) &&
                Objects.equals(medications, fireDTO.medications) &&
                Objects.equals(allergies, fireDTO.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, age, phone, medications, allergies);
    }
}