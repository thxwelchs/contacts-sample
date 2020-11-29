package com.osci.contacts.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends BaseEntity<Integer> {
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Contact> contacts = new HashSet<>();

    public void addGroup(Group group) {
        groups.add(group);
        group.setUser(this);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setUser(this);
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

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
}
