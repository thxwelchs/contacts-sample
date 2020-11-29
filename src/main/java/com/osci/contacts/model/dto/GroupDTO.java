package com.osci.contacts.model.dto;

import com.osci.contacts.model.entity.BaseEntity;
import com.osci.contacts.model.entity.Contact;
import com.osci.contacts.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "주소록 그룹")
public class GroupDTO extends BaseEntity<Integer> {
    @Size(message = "그룹명은 최소 1글자 최대 10글자 제한입니다.", min = 1, max = 10)
    private String name;
    private Set<Contact> contacts = new HashSet<>();
    private Integer userId;

    @ApiModelProperty(hidden = true)
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
        if(user == null) {
            user = new User();
            user.setId(userId);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public void update(GroupDTO groupDTO) {
        try {
            Field[] allFields = this.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAccessible() && field.get(groupDTO) != null) {
                    field.set(this, field.get(groupDTO));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
