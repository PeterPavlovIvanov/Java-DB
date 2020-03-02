package com.softuni.srpingintroexercise.services;

import com.softuni.srpingintroexercise.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;
    int getAllAuthorsCount();
    Author findAuthorById(long id);
    List<Author> getAllAuthorsByBooksCount();
    List<Author> getAllAuthors();
}
