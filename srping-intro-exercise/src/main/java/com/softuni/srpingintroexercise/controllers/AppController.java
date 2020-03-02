package com.softuni.srpingintroexercise.controllers;

import com.softuni.srpingintroexercise.entities.Author;
import com.softuni.srpingintroexercise.entities.Book;
import com.softuni.srpingintroexercise.services.AuthorService;
import com.softuni.srpingintroexercise.services.BookService;
import com.softuni.srpingintroexercise.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public AppController(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        //seed data
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();

        //Exercise 1
        this.exercise1_1();
        //this.exercise1_2();
        //this.exercise1_3();
        //this.exercise1_4();

    }

    private void exercise1_1() {
        this.bookService.getAllBooksAfter2000().forEach(book -> {
            System.out.println(book.getTitle());
        });
    }

    private void exercise1_2() {
        List<Author> authors = this.authorService.getAllAuthors();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("01/01/1990", formatter);

        for (Author author : authors) {
            for (Book b : author.getBooks()) {
                if (b.getReleaseDate().isBefore(releaseDate)) {
                    System.out.println(author.getFirstName() + " " + author.getLastName());
                }
            }
        }
    }

    private void exercise1_3() {
        this.authorService.getAllAuthorsByBooksCount().forEach(author -> {
            System.out.printf("%s %s %d %n"
                    , author.getFirstName(), author.getLastName(), author.getBooks().size());
        });
    }

    private void exercise1_4() {
        this.bookService.getAllByGeorgePowell().forEach(book -> {
            System.out.println(book.getTitle() + " " + book.getReleaseDate() + " " + book.getCopies());
        });
    }

}
