package com.osci.contacts.model.dto;

import com.osci.contacts.annotation.Phone;
import com.osci.contacts.model.entity.BaseEntity;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

@ApiModel(description = "주소록")
public class ContactDTO extends BaseEntity<Integer> {
    @NotEmpty @Size(message = "이름은 최소 2글자, 최대 20글자 제한입니다.", min = 2, max = 20)
    private String name;

    @Phone
    private String phone;

    @Email(message = "올바른 이메일 양식이 아닙니다.")
    private String email;

    private Boolean important;
    private Integer groupId;
    @ApiModelProperty(required = true)
    private Integer userId;

    private Group group;
    private User user;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
        if(group == null) {
            group = new Group();
            group.setId(groupId);
        }
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getImportant() {
        return important;
    }

    public void setImportant(Boolean important) {
        this.important = important;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void update(ContactDTO contactDTO) {
        try {
            Field[] allFields = this.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                if (field.isAccessible() && field.get(contactDTO) != null) {
                    field.set(this, field.get(contactDTO));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
