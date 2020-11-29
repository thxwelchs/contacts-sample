package com.osci.contacts.service;

import com.osci.contacts.model.dto.ContactDTO;
import com.osci.contacts.model.entity.Contact;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.model.mapper.ContactMapper;
import com.osci.contacts.repository.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }


    public Page<ContactDTO> getContacts(User user, Pageable pageable) {
        return contactRepository.findByUser(user, pageable).map((contact) -> contactMapper.toDTO(contact));
    }

    public Page<ContactDTO> getContactsByGroup(Group group, Pageable pageable) {
        return contactRepository.findByGroup(group, pageable).map((contact) -> contactMapper.toDTO(contact));
    }

    public ContactDTO findById(Integer id) {
        Contact contact = contactRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return contactMapper.toDTO(contact);
    }

    public ContactDTO save(Contact contact) {
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    public ContactDTO save(ContactDTO contactDTO) {
        Contact contact = contactRepository.save(contactMapper.toEntity(contactDTO));
        return contactMapper.toDTO(contact);
    }

    public void deleteById(Integer id) {
        contactRepository.deleteById(id);
    }
}
