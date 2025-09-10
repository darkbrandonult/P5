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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirestationCoverageDTO that = (FirestationCoverageDTO) o;
        return childCount == that.childCount &&
                adultCount == that.adultCount &&
                java.util.Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(persons, childCount, adultCount);
    }
}
