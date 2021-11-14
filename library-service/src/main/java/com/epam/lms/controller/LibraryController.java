package com.epam.lms.controller;

import com.epam.lms.client.BookClient;
import com.epam.lms.client.UserClient;
import com.epam.lms.dto.BookDto;
import com.epam.lms.dto.LibraryDto;
import com.epam.lms.dto.UserBookDetails;
import com.epam.lms.dto.UserDto;
import com.epam.lms.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookClient bookClient;

    @Autowired
    private UserClient userClient;

    @GetMapping(value = "/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtos = bookClient.getAll();
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/books/{bookId}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long bookId) {
        BookDto bookDto = bookClient.get(bookId);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping(value = "/books")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        BookDto savedBookdto = bookClient.add(bookDto);
        return new ResponseEntity<>(savedBookdto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/books/{bookId}")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto, @PathVariable Long bookId) {
        BookDto updatedBookDto = bookClient.update(bookDto, bookId);
        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/books/{bookId}")
    public ResponseEntity<String> delete(@PathVariable Long bookId) {
        String message = bookClient.delete(bookId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userClient.getAll();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{username}")
    public ResponseEntity<UserBookDetails> getUserBookDetails(@PathVariable String username) {
        UserBookDetails userBookDetails = new UserBookDetails();
        UserDto userDto = userClient.get(username);
        userBookDetails.setUserDto(userDto);
        List<LibraryDto> libraryRecords = libraryService.getRecordsByUsername(username);
        for (LibraryDto libraryDto : libraryRecords) {
            BookDto bookDto = bookClient.get(libraryDto.getBookId());
            userBookDetails.addBook(bookDto);
        }
        return new ResponseEntity<>(userBookDetails, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userClient.add(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/users/{username}")
    public ResponseEntity<String> deleteUserBooks(@PathVariable String username) {
        String message = userClient.delete(username);
        List<LibraryDto> libraryRecords = libraryService.getRecordsByUsername(username);
        for (LibraryDto libraryDto : libraryRecords) {
            libraryService.delete(libraryDto.getId());
        }
        return new ResponseEntity<>("User deleted and books are released", HttpStatus.OK);
    }

    @PutMapping(value = "/users/{username}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String username) {
        UserDto updatedUserDto = userClient.update(userDto, username);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{username}/books/{bookId}")
    public ResponseEntity<LibraryDto> issueBook(@PathVariable String username, @PathVariable Long bookId) {
        bookClient.get(bookId);
        userClient.get(username);
        LibraryDto libraryDto = new LibraryDto();
        libraryDto.setUsername(username);
        libraryDto.setBookId(bookId);
        LibraryDto savedLibraryDto = libraryService.save(libraryDto);
        return new ResponseEntity<>(savedLibraryDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/users/{username}/books/{bookId}")
    public ResponseEntity<String> releaseBook(@PathVariable String username, @PathVariable Long bookId) {
        LibraryDto libraryDto = libraryService.getRecordByUsernameAndBookId(username, bookId);
        libraryService.delete(libraryDto.getId());
        return new ResponseEntity<>("Book released", HttpStatus.OK);
    }
}
