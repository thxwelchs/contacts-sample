package com.osci.contacts.model.dto;

import com.osci.contacts.model.entity.Group;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

public class ContactSaveOnlyGroup {
    private Integer groupId;
    @ApiModelProperty(hidden = true)
    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
        group = new Group();
        group.setId(groupId);
    }
}
