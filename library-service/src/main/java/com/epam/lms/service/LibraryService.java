package com.epam.lms.service;

import com.epam.lms.dao.LibraryRepository;
import com.epam.lms.dto.LibraryDto;
import com.epam.lms.entity.Library;
import com.epam.lms.exception.BookLimitExcededException;
import com.epam.lms.mapper.LibraryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryMapper libraryMapper;


    public LibraryDto save(LibraryDto libraryDto) {
        List<Library> libraryRecords = libraryRepository.findByUsername(libraryDto.getUsername());
        if(libraryRecords.size()>=3){
            throw new BookLimitExcededException("At the most 3 books allowed per user");
        }
        Library library = libraryRepository.save(libraryMapper.libraryDtoToLibrary(libraryDto));
        return libraryMapper.libraryToLibraryDto(library);
    }


    public void delete(Long id) {
        libraryRepository.deleteById(id);
    }

    public List<LibraryDto> getRecordsByUsername(String username) {
        List<Library> libraryRecords = libraryRepository.findByUsername(username);
        return libraryMapper.mapLibrariesToLibraryDtos(libraryRecords);
    }

    public LibraryDto getRecordByUsernameAndBookId(String username, Long bookId) {
        Library library = libraryRepository.findByUsernameAndBookId(username, bookId);
        return libraryMapper.libraryToLibraryDto(library);
    }
}
