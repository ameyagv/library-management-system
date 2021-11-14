package com.epam.lms.service;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.epam.lms.dao.LibraryRepository;
import com.epam.lms.entity.Library;
import com.epam.lms.exception.BookLimitExcededException;
import com.epam.lms.mapper.LibraryMapper;
import com.epam.lms.mapper.LibraryMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {LibraryMapperImpl.class, LibraryService.class})
class LibraryServiceTest {

    @InjectMocks
    @Autowired
    private LibraryService libraryService;

    @MockBean
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryMapper libraryMapper;

    @Test
    void save() {
        Library library = new Library(1L, "ameyagv", 1L);
        when(libraryRepository.save(any(Library.class))).thenReturn(library);
        assertEquals(libraryMapper.libraryToLibraryDto(library), libraryService.save(libraryMapper.libraryToLibraryDto(library)));
        verify(libraryRepository, times(1)).save(any(Library.class));
    }

    @Test
    void saveException() {
        List<Library> libraryRecords = Arrays.asList(new Library(1L, "ameyagv", 1L), new Library(1L, "ameyagv", 1L), new Library(1L, "ameyagv", 1L), new Library(1L, "ameyagv", 1L));
        when(libraryRepository.findByUsername(anyString())).thenReturn(libraryRecords);
        Exception exception = assertThrows(BookLimitExcededException.class, () -> {
            libraryService.save(libraryMapper.libraryToLibraryDto(libraryRecords.get(0)));
        });
        String expectedMessage = "At the most 3 books allowed per user";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        libraryService.delete(1L);
        verify(libraryRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getRecordsByUsername() {
        List<Library> libraryList = Arrays.asList(new Library(1L, "ameyagv", 1L));
        when(libraryRepository.findByUsername(anyString())).thenReturn(libraryList);
        assertEquals(libraryMapper.mapLibrariesToLibraryDtos(libraryList), libraryService.getRecordsByUsername("ameyagv"));
        verify(libraryRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void getRecordByUsernameAndBookId() {
        Library library = new Library(1L, "ameyagv", 1L);
        when(libraryRepository.findByUsernameAndBookId(anyString(), anyLong())).thenReturn(library);
        assertEquals(libraryMapper.libraryToLibraryDto(library), libraryService.getRecordByUsernameAndBookId("ameyagv", 1L));
        verify(libraryRepository, times(1)).findByUsernameAndBookId(anyString(), anyLong());
    }
}