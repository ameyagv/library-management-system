package com.epam.lms.mapper;

import com.epam.lms.dto.BookDto;
import com.epam.lms.dto.LibraryDto;
import com.epam.lms.entity.Library;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    LibraryDto libraryToLibraryDto(Library library);

    Library libraryDtoToLibrary(LibraryDto libraryDto);

    List<LibraryDto> mapLibrariesToLibraryDtos(List<Library> libraries);

    List<Library> mapLibraryDtosToLabraries(List<BookDto> bookDtos);
}
