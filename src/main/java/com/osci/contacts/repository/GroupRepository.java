package com.osci.contacts.repository;

import com.osci.contacts.model.entity.Contact;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByUser(User user, Sort sort);
}
