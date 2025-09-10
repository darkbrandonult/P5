package com.safetynet.alerts.model;

import java.util.Objects;

public class Firestation {
    private String address;
    private String station;

    public Firestation() {}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestation that = (Firestation) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
