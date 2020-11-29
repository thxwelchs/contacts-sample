package com.osci.contacts.model.mapper;

import java.util.List;

public interface EntityMapper<DTO, E> {
    DTO toDTO(E e);
    E toEntity(DTO dto);
}
