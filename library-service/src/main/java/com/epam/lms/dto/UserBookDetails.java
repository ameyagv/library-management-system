package com.epam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookDetails {
    private UserDto userDto;

    private final List<BookDto> books = createList();

    private List<BookDto> createList() {
        if (books == null) {
            return new ArrayList<>();
        }
        return books;
    }

    public void addBook(BookDto book) {

        books.add(book);
    }
}
