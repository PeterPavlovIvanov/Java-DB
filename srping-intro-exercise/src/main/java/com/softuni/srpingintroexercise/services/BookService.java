package com.softuni.srpingintroexercise.services;

import com.softuni.srpingintroexercise.entities.Book;

import java.io.IOException;
import java.util.List;

public interface BookService  {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
    List<Book> getAllByGeorgePowell();
}
