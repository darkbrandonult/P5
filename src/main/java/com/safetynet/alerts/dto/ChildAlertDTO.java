package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.Person;

public class ChildAlertDTO {
    private List<Person> children;
    private List<Person> adults;

    public ChildAlertDTO() {}

    public ChildAlertDTO(List<Person> children, List<Person> adults) {
        this.children = children;
        this.adults = adults;
    }

    public List<Person> getChildren() { return children; }
    public void setChildren(List<Person> children) { this.children = children; }
    public List<Person> getAdults() { return adults; }
    public void setAdults(List<Person> adults) { this.adults = adults; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildAlertDTO that = (ChildAlertDTO) o;
        return java.util.Objects.equals(children, that.children) &&
                java.util.Objects.equals(adults, that.adults);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(children, adults);
    }
}
