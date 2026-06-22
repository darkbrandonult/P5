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
}
