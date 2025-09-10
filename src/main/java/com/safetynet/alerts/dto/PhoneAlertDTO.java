package com.safetynet.alerts.dto;

public class PhoneAlertDTO {
    private String phone;

    public PhoneAlertDTO() {}
    public PhoneAlertDTO(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}