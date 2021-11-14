package com.epam.lms.client;

import com.epam.lms.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookClient {
    @GetMapping(value = "books")
    public List<BookDto> getAll();

    @GetMapping(value = "books/{bookId}")
    public BookDto get(@PathVariable(value = "bookId") Long bookId);

    @PostMapping(value = "books")
    public BookDto add(BookDto bookDto);

    @PutMapping(value = "books/{bookId}")
    public BookDto update(BookDto bookDto, @PathVariable(value = "bookId") Long bookId);

    @DeleteMapping(value = "books/{bookId}")
    public String delete(@PathVariable(value = "bookId") Long bookId);
}
