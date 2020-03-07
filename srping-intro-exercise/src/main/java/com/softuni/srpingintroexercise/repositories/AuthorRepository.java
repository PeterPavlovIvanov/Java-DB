package com.softuni.srpingintroexercise.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.softuni.srpingintroexercise.entities.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("SELECT a FROM Author AS a ORDER BY a.books.size DESC")
    List<Author> getAllAuthorsByBooksCount();

    @Query("SELECT a FROM Author AS a")
    List<Author> findAllAuthors();

    List<Author> findAllByFirstNameEndingWith(String end);

    List<Author> findAllByLastNameStartingWith(String str);
}
