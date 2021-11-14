package com.epam.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIBRARY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private Long bookId;
}
