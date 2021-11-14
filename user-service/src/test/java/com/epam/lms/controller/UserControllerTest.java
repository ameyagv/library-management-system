package com.epam.lms.controller;

import com.epam.lms.dto.UserDto;
import com.epam.lms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAll() throws Exception {
        List<UserDto> userDtos = new ArrayList<>(Arrays.asList(new UserDto("ameyagv", "ameya@gmail.com", "Ameya")));
        when(userService.listAll()).thenReturn(userDtos);
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].username", Matchers.equalTo("ameyagv")));
        verify(userService, times(1)).listAll();
    }

    @Test
    void gettest() throws Exception {
        UserDto userDto = new UserDto("ameyagv", "ameya@gmail.com", "Ameya");
        when(userService.get(anyString())).thenReturn(userDto);
        mockMvc.perform(get("/users/ameyagv")).andExpect(status().isOk());
        verify(userService, times(1)).get(any(String.class));
    }

    @Test
    void save() throws Exception {
        UserDto userDto = new UserDto("ameyagv", "ameya@gmail.com", "Ameya");
        when(userService.save(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(mapper.writeValueAsString(userDto)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        verify(userService, times(1)).save(any(UserDto.class));
    }

    @Test
    void update() throws Exception {
        UserDto userDto = new UserDto("ameyagv", "ameya@gmail.com", "Ameya");
        when(userService.update(any(UserDto.class))).thenReturn(userDto);
        mockMvc.perform(put("/users/ameyagv").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(mapper.writeValueAsString(userDto)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(userService, times(1)).update(any(UserDto.class));

    }

    @Test
    void deletetest() throws Exception {
        mockMvc.perform(delete("/users/ameyagv")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString().equals("User deleted");
        verify(userService, times(1)).delete(anyString());
    }
}