package com.softuni.srpingintroexercise.services;


import com.softuni.srpingintroexercise.entities.Author;
import com.softuni.srpingintroexercise.entities.Book;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public interface BookService  {
    void seedBooks() throws IOException;
    List<Book> getAllBooksAfter2000();
    List<Book> getAllByGeorgePowell();
    List<Book> getAllByAgeRestriction();
    List<Book> getAllByCopiesLessThan5k();
    List<Book> getAllByPrice();
    List<Book> getAllByReleaseDateNotInYear();
    List<Book> getAllByReleaseDateBefore();
    List<Book> getAllByTitleContaining();
    LinkedHashMap<Author, List<Book>> getAllByAuthorBooksForExercise8();
    int getAllBooksCountByTitleLongerThan(int length);
    LinkedHashMap<Author, List<Book>> getAllByAuthorBooksForExercise10();
    Book getBookInfoByTitle(String title);
}
