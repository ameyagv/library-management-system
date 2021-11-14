package com.epam.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String username;
    private String email;
    private String name;
}
