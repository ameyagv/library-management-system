package com.epam.lms.service;

import com.epam.lms.dao.UserRepository;
import com.epam.lms.entity.User;
import com.epam.lms.exception.UserAlreadyExistsException;
import com.epam.lms.mapper.UserMapper;
import com.epam.lms.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserMapperImpl.class, UserService.class})
class UserServiceTest {

    @InjectMocks
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
//    @MockBean
    private UserMapper userMapper;

    @Test
    void listAll() {
        List<User> users = Arrays.asList(new User("ameyagv", "ameya@gmail.com", "Ameya"));
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userMapper.mapUsersToUserDtos(users), userService.listAll());
    }

    @Test
    void get() {
        User user = new User("ameyagv", "ameya@gmail.com", "Ameya");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertEquals(userMapper.userToUserDto(user), userService.get("ameyagv"));
    }

    @Test
    void save() {
        User user = new User("ameyagv", "ameya@gmail.com", "Ameya");
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(userMapper.userToUserDto(user), userService.save(userMapper.userToUserDto(user)));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveFail() {
        User user = new User("ameyagv", "ameya@gmail.com", "Ameya");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.save(userMapper.userToUserDto(user));
        });
    }

    @Test
    void update() {
        User user = new User("ameyagv", "ameya@gmail.com", "Ameya");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        assertEquals(userMapper.userToUserDto(user), userService.update(userMapper.userToUserDto(user)));
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void delete() {
        User user = new User("ameyagv", "ameya@gmail.com", "Ameya");
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        userService.delete("ameyagv");
        verify(userRepository, times(1)).deleteById(anyString());

    }
}