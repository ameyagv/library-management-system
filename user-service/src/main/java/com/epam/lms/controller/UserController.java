package com.epam.lms.controller;

import com.epam.lms.dto.UserDto;
import com.epam.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = userService.listAll();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        UserDto userDto = userService.get(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.save(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{username}")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable String username) {
        userDto.setUsername(username);
        UserDto updatedUserDto = userService.update(userDto);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        userService.delete(username);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

}
