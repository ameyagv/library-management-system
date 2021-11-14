package com.epam.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {
    private Long id;
    private String username;
    private Long bookId;
}
