package com.epam.lms.service;

import com.epam.lms.dao.BookRepository;
import com.epam.lms.entity.Book;
import com.epam.lms.mapper.BookMapper;
import com.epam.lms.mapper.BookMapperImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookMapperImpl.class, BookService.class})
class BookServiceTest {

    @InjectMocks
    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
//    @MockBean
    private BookMapper bookMapper;

    @Test
    void listAll() {
        List<Book> books = new ArrayList<>(Arrays.asList(new Book(1L, "Java", "Oreilly", "Bert")));
        when(bookRepository.findAll()).thenReturn(books);
        assertEquals(bookMapper.mapBooksToBookDtos(books), bookService.listAll());
    }

    @Test
    void get() {
        Book book = new Book(1L, "Java", "Oreilly", "Bert");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        assertEquals(bookMapper.bookToBookDto(book), bookService.get(1L));
    }

    @Test
    void save() {
        Book book = new Book(1L, "Java", "Oreilly", "Bert");
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        assertEquals(bookMapper.bookToBookDto(book), bookService.save(bookMapper.bookToBookDto(book)));
        verify(bookRepository, times(1)).save(any(Book.class));
    }


    @Test
    void update() {
        Book book = new Book(1L, "Java", "Oreilly", "Bert");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        assertEquals(bookMapper.bookToBookDto(book), bookService.update(bookMapper.bookToBookDto(book)));
        verify(bookRepository, times(1)).save(any(Book.class));

    }

    @Test
    void delete() {
        Book book = new Book(1L, "Java", "Oreilly", "Bert");
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        bookService.delete(1L);
        verify(bookRepository, times(1)).deleteById(anyLong());

    }
}