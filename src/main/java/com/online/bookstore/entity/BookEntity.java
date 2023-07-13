package com.online.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity extends GenericEntity<Long> {

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false, unique = true)
    private String isbn;
}
