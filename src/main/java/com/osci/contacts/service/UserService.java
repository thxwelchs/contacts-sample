package com.osci.contacts.service;

import com.osci.contacts.model.dto.UserDTO;
import com.osci.contacts.model.entity.User;
import com.osci.contacts.model.mapper.UserMapper;
import com.osci.contacts.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userRepository.save(userMapper.toEntity(userDTO));
        return userMapper.toDTO(user);
    }

    public UserDTO findUserDTOByUserId(String userId) {
        return userMapper.toDTO(findByUserId(userId));
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public UserDTO findUserDTOById(Integer id) {
        return userMapper.toDTO(findById(id));
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
