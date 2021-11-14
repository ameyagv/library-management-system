package com.epam.lms.controller;

import com.epam.lms.client.BookClient;
import com.epam.lms.client.UserClient;
import com.epam.lms.dto.BookDto;
import com.epam.lms.dto.LibraryDto;
import com.epam.lms.dto.UserBookDetails;
import com.epam.lms.dto.UserDto;
import com.epam.lms.service.LibraryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes = GlobalExceptionHandler.class)
@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @MockBean
    private BookClient bookClient;

    @MockBean
    private UserClient userClient;

    private ObjectMapper mapper = new ObjectMapper();

    private final String BASE_URL_BOOKS = "http://localhost:8080/books";

    private BookDto bookDto;
    private List<UserDto> userDtos;
    private UserDto userDto;
    private List<LibraryDto> libraryRecords;
    private UserBookDetails userBookDetails;
    private LibraryDto libraryDto;
    private List<BookDto> bookDtos;

    @BeforeEach
    public void setup() {
        bookDto = new BookDto(1L, "Java", "Oreilly", "Bert");
        userDtos = Arrays.asList(new UserDto("ameyagv", "ameya@gmail.com", "Ameya"));
        libraryRecords = Arrays.asList(new LibraryDto(1L, "ameyagv", 1L));
        userBookDetails = new UserBookDetails();
        userBookDetails.setUserDto(userDtos.get(0));
        userBookDetails.addBook(bookDto);
        userDto = userDtos.get(0);
        libraryDto = libraryRecords.get(0);
        bookDtos = Arrays.asList(new BookDto(1L, "Java", "Oreilly", "Bert"));
    }


    @Test
    void getAllBooks() throws Exception {
        ResponseEntity<List<BookDto>> response = new ResponseEntity<>(bookDtos, HttpStatus.OK);
        when(bookClient.getAll()).thenReturn(bookDtos);
        String contentAsString = mockMvc.perform(get("/books")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<BookDto> responseBookDtos = mapper.readValue(contentAsString, new TypeReference<List<BookDto>>() {
        });
        assertEquals(bookDtos, responseBookDtos);
        verify(bookClient).getAll();
    }

        @Test
    void getBook() throws Exception {
        when(bookClient.get(anyLong())).thenReturn(bookDto);
        mockMvc.perform(get("/books/1")).andExpect(status().isOk());
        verify(bookClient).get(anyLong());
    }


    @Test
    void addBook() throws Exception {
        when(bookClient.add(any(BookDto.class)))
                .thenReturn(bookDto);
        mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(bookClient).add(any(BookDto.class));
    }

    @Test
    void update() throws Exception {
        when(bookClient.update(any(BookDto.class), anyLong()))
                .thenReturn(bookDto);
        mockMvc.perform(put("/books/1").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookClient).update(any(BookDto.class), anyLong());

    }

    @Test
    void deletetest() throws Exception {
        when(bookClient.delete(anyLong()))
                .thenReturn("Deleted");
        mockMvc.perform(delete("/books/1")).andExpect(status().isOk());
        verify(bookClient).delete(anyLong());
    }

    @Test
    void getAllUsers() throws Exception {
        when(userClient.getAll()).thenReturn(userDtos);
        mockMvc.perform(get("/users")).andExpect(status().isOk());
        verify(userClient).getAll();
    }

    @Test
    void getUserBookDetails() throws Exception {
        doAnswer(q -> userDto).when(userClient).get(anyString());
        when(libraryService.getRecordsByUsername(anyString())).thenReturn(libraryRecords);
        doAnswer(q -> bookDto).when(bookClient).get(1L);
        String contentAsString = mockMvc.perform(get("/users/ameyagv")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        UserBookDetails readValue = mapper.readValue(contentAsString, UserBookDetails.class);
        assertEquals(userBookDetails, readValue);
    }

    @Test
    void addUser() throws Exception {
        doReturn(userDto).when(userClient).add(any(UserDto.class));
        String contentAsString = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        UserDto readValue = mapper.readValue(contentAsString, UserDto.class);
        assertEquals(userDto, readValue);
        verify(userClient, times(1)).add(any(UserDto.class));
    }

    @Test
    void deleteUserBooks() throws Exception {
        when(userClient.delete(anyString()))
                .thenReturn("Deleted");
        when(libraryService.getRecordsByUsername(anyString())).thenReturn(libraryRecords);
        mockMvc.perform(delete("/users/ameyagv")).andExpect(status().isOk());
        verify(libraryService).delete(anyLong());
    }

    @Test
    void updateUser() throws Exception {
        when(userClient.update(any(UserDto.class), anyString()))
                .thenReturn(userDto);
        mockMvc.perform(put("/users/ameyagv").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userClient).update(any(UserDto.class), anyString());

    }

    @Test
    void issueBook() throws Exception {
        when(libraryService.save(any(LibraryDto.class))).thenReturn(libraryDto);
        mockMvc.perform(post("/users/ameyagv/books/1")).andExpect(status().isCreated());
        verify(libraryService, times(1)).save(any(LibraryDto.class));
    }

    @Test
    void releaseBook() throws Exception {
        when(libraryService.getRecordByUsernameAndBookId(anyString(), anyLong())).thenReturn(libraryDto);
        mockMvc.perform(delete("/users/ameyagv/books/1")).andExpect(status().isOk());
        verify(libraryService).delete(anyLong());
    }
}