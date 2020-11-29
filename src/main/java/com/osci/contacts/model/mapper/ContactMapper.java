package com.osci.contacts.model.mapper;

import com.osci.contacts.model.dto.ContactDTO;
import com.osci.contacts.model.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactMapper extends EntityMapper<ContactDTO, Contact>{
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    @Mapping(target = "group", ignore = true)
    ContactDTO toDTOWithoutGroup(Contact contact);
}
