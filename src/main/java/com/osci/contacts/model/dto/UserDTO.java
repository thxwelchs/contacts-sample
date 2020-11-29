package com.osci.contacts.model.dto;

import com.osci.contacts.model.entity.BaseEntity;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

@ApiModel(description = "유저")
public class UserDTO extends BaseEntity<Integer> {
    @NotEmpty @Size(message = "유저아이디는 최소 4, 최대 4글자 제한입니다.", min = 4, max = 12)
    private String userId;
    @NotEmpty @Size(message = "유저아이디는 최소 2, 최대 20글자 제한입니다.", min = 2, max = 20)
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(UserDTO userDTO) {
        try {
            Field[] allFields = this.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAccessible() && field.get(userDTO) != null) {
                    field.set(this, field.get(userDTO));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
