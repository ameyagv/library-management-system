package com.epam.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BOOK")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "BOOK_ID", nullable = false)
    @GeneratedValue
    private Long id;
    private String name;
    private String publisher;
    private String author;
}
