package com.osci.contacts.model.mapper;

import com.osci.contacts.model.dto.UserDTO;
import com.osci.contacts.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
