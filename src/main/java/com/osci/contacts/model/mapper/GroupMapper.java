package com.osci.contacts.model.mapper;

import com.osci.contacts.model.dto.GroupDTO;
import com.osci.contacts.model.entity.Group;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {
    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

//    @IterableMapping(qualifiedByName = "toDTO")
//    List<GroupDTO> toDTOList(List<Group> groups);

//    @IterableMapping(qualifiedByName = "toDTOWithoutContacts")
//    List<GroupDTO> listWithoutContacts(List<Group> groups);

    @Mapping(target = "contacts", ignore = true)
    GroupDTO toDTOWithoutContacts(Group group);
}
