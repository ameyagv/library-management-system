package com.epam.lms.client;

import com.epam.lms.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping(value = "users")
    public List<UserDto> getAll();

    @GetMapping(value = "users/{username}")
    public UserDto get(@PathVariable(value = "username") String username);

    @PostMapping(value = "users")
    public UserDto add(@RequestBody UserDto userDto);

    @DeleteMapping(value = "users/{username}")
    public String delete(@PathVariable(value = "username") String username);

    @PutMapping(value = "users/{username}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable(value = "username") String username);
}
