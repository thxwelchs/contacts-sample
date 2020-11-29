package com.osci.contacts.model.dto;

import com.osci.contacts.annotation.Phone;

public class ContactSaveOnlyPhone {
    @Phone
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
