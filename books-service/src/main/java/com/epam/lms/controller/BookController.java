package com.epam.lms.controller;

import com.epam.lms.dto.BookDto;
import com.epam.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public ResponseEntity<List<BookDto>> getAll() {
        List<BookDto> bookDtos = bookService.listAll();
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookDto> get(@PathVariable Long id) {
        BookDto bookDto = bookService.get(id);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping(value = "/books")
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) {
        BookDto savedBookDto = bookService.save(bookDto);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/books/{id}")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto, @PathVariable Long id) {
        bookDto.setId(id);
        BookDto updatedBookDto = bookService.update(bookDto);
        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }

}
