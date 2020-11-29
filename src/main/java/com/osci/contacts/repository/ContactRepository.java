package com.osci.contacts.repository;

import com.osci.contacts.model.entity.Contact;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    Page<Contact> findByUser(User user, Pageable pageable);
    Page<Contact> findByGroup(Group group, Pageable pageable);
}
