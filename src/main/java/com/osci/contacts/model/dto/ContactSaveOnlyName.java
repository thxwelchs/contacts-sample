package com.osci.contacts.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ContactSaveOnlyName {
    @NotEmpty @Size(message = "이름은 최소 2글자, 최대 20글자 제한입니다.", min = 2, max = 20)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
