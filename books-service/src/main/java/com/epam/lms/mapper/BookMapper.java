package com.epam.lms.mapper;

import com.epam.lms.dto.BookDto;
import com.epam.lms.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);

    List<BookDto> mapBooksToBookDtos(List<Book> books);

    List<Book> mapBookDtosToBooks(List<BookDto> bookDtos);
}
