package com.epam.lms.dao;

import com.epam.lms.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    List<Library> findByUsername(String username);

    Library findByUsernameAndBookId(String username, Long bookId);
}
