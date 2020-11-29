package com.osci.contacts.service;

import com.osci.contacts.model.dto.GroupDTO;
import com.osci.contacts.model.entity.Group;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.model.mapper.GroupMapper;
import com.osci.contacts.repository.GroupRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    public GroupDTO save(GroupDTO groupDTO) {
        return groupMapper.toDTO(groupRepository.save(toEntity(groupDTO)));
    }

    public GroupDTO findGroupDTOById(Integer id) {
        return groupMapper.toDTO(findById(id));
    }

    public Group findById(Integer id) {
        return groupRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteById(Integer id) {
        groupRepository.deleteById(id);
    }

    public List<GroupDTO> getGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(groupMapper::toDTOWithoutContacts).collect(Collectors.toList());
    }

    public List<GroupDTO> getGroupsByUser(User user, Sort sort) {
        List<Group> groups = groupRepository.findByUser(user, sort);
        return groups.stream().map(groupMapper::toDTOWithoutContacts).collect(Collectors.toList());
    }

    public Group toEntity(GroupDTO groupDTO) {
        return groupMapper.toEntity(groupDTO);
    }
}
