package com.epam.lms.service;

import com.epam.lms.dao.UserRepository;
import com.epam.lms.dto.UserDto;
import com.epam.lms.entity.User;
import com.epam.lms.exception.UserAlreadyExistsException;
import com.epam.lms.exception.UserNotFoundException;
import com.epam.lms.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDto> listAll() {
        List<User> users = userRepository.findAll();
        return userMapper.mapUsersToUserDtos(users);
    }

    public UserDto get(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.userToUserDto(user);
    }

    public UserDto save(UserDto userDto) {
        if (userRepository.findById(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username " + userDto.getUsername() + " already exists");
        }
        User user = userRepository.save(userMapper.userDtoToUser(userDto));
        return userMapper.userToUserDto(user);
    }

    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        User user1 = userRepository.save(userMapper.userDtoToUser(userDto));
        return userMapper.userToUserDto(user1);
    }

    public void delete(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(username);
    }
}
