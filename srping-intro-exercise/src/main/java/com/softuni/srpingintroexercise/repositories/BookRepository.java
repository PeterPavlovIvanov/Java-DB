package com.softuni.srpingintroexercise.repositories;


import com.softuni.srpingintroexercise.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.softuni.srpingintroexercise.entities.AgeRestriction;
import com.softuni.srpingintroexercise.entities.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByReleaseDateAfter(LocalDate localDate);
    @Query("SELECT b FROM Book AS b WHERE concat(b.author.firstName,' ',b.author.lastName) = 'George Powell'" +
            "ORDER BY b.releaseDate DESC, b.title ASC ")
    List<Book> findAllByGeorgePowell();

    List<Book> findAllByAgeRestriction(AgeRestriction type);

    List<Book> findAllByCopiesLessThan(int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal f, BigDecimal s);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContaining(String str);

    List<Book> findAllByAuthor(Author author);

    List<Book> findAllBy();

    @Query("SELECT b FROM Book AS b")
    List<Book> findBooksInformation();
}
