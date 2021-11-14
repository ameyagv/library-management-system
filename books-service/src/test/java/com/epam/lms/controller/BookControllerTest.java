package com.epam.lms.controller;

import com.epam.lms.dto.BookDto;
import com.epam.lms.service.BookService;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void getAll() throws Exception {
        // given
        List<BookDto> bookDtos = new ArrayList<>(Arrays.asList(new BookDto(1L, "Java", "Oreilly", "Bert")));
        when(bookService.listAll()).thenReturn(bookDtos);
        // when
        mockMvc.perform(get("/books")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Java")));
        // then
        verify(bookService, times(1)).listAll();
        // verify ResponseEntity
        // verify RE bookDtos
        // verify RE HttpStatus
    }

    @Test
    void gettest() throws Exception {
        BookDto bookDto = new BookDto(1L, "Java", "Oreilly", "Bert");
        when(bookService.get(anyLong())).thenReturn(bookDto);
        mockMvc.perform(get("/books/1")).andExpect(status().isOk());
        verify(bookService, times(1)).get(anyLong());
    }

    @Test
    void save() throws Exception {
        BookDto bookDto = new BookDto(1L, "Java", "Oreilly", "Bert");
        when(bookService.save(any(BookDto.class))).thenReturn(bookDto);
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(mapper.writeValueAsString(bookDto)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
        verify(bookService, times(1)).save(any(BookDto.class));
    }

    @Test
    void update() throws Exception {
        BookDto bookDto = new BookDto(1L, "Java", "Oreilly", "Bert");
        when(bookService.update(any(BookDto.class))).thenReturn(bookDto);
        mockMvc.perform(put("/books/1").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(mapper.writeValueAsString(bookDto)).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bookService, times(1)).update(any(BookDto.class));

    }

    @Test
    void deletetest() throws Exception {
        mockMvc.perform(delete("/books/1")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString().equals("Book deleted");
        verify(bookService, times(1)).delete(anyLong());
    }
}