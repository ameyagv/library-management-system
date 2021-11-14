package com.epam.lms.service;

import com.epam.lms.dao.BookRepository;
import com.epam.lms.dto.BookDto;
import com.epam.lms.entity.Book;
import com.epam.lms.exception.BookNotFoundException;
import com.epam.lms.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<BookDto> listAll() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.mapBooksToBookDtos(books);
    }

    public BookDto get(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        return bookMapper.bookToBookDto(book);
    }

    public BookDto save(BookDto bookDto) {
        Book book = bookRepository.save(bookMapper.bookDtoToBook(bookDto));
        return bookMapper.bookToBookDto(book);
    }

    public BookDto update(BookDto bookDto) {
        Book book1 = bookRepository.findById(bookDto.getId()).orElseThrow(() -> new BookNotFoundException("Book not found"));
        Book book = bookRepository.save(bookMapper.bookDtoToBook(bookDto));
        return bookMapper.bookToBookDto(book);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.deleteById(id);
    }
}
