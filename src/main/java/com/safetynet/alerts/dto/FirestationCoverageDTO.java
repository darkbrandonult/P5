package com.safetynet.alerts.dto;

import java.util.List;
import com.safetynet.alerts.model.Person;

public class FirestationCoverageDTO {
    private List<Person> persons;
    private int childCount;
    private int adultCount;

    public FirestationCoverageDTO() {}

    public FirestationCoverageDTO(List<Person> persons, int childCount, int adultCount) {
        this.persons = persons;
        this.childCount = childCount;
        this.adultCount = adultCount;
    }

    public List<Person> getPersons() { return persons; }
    public void setPersons(List<Person> persons) { this.persons = persons; }
    public int getChildCount() { return childCount; }
    public void setChildCount(int childCount) { this.childCount = childCount; }
    public int getAdultCount() { return adultCount; }
    public void setAdultCount(int adultCount) { this.adultCount = adultCount; }
}
