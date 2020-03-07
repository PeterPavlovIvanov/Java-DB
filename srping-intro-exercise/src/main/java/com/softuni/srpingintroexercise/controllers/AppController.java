package com.softuni.srpingintroexercise.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import com.softuni.srpingintroexercise.entities.Author;
import com.softuni.srpingintroexercise.entities.Book;
import com.softuni.srpingintroexercise.entities.EditionType;
import com.softuni.srpingintroexercise.services.AuthorService;
import com.softuni.srpingintroexercise.services.BookService;
import com.softuni.srpingintroexercise.services.CategoryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        //EXERCISE: SPRING DATA INTRO
        //Exercise 1
        //this.exercise1_1();
        //this.exercise1_2();
        //this.exercise1_3();
        //this.exercise1_4();

        //Exercises: Spring Data Advanced Quering
        //exerciseOne();
        //exerciseTwo();
        //exerciseThree();
        //exerciseFour();
        //exerciseFive();
        //exerciseSix();
        //exerciseSeven();
        //exerciseEight();
        //exerciseNine();
        //exerciseTen();
        //exerciseEleven();
    }

    private void exerciseOne() {
        System.out.println("Enter an Age Restriction: ");
        this.bookService.getAllByAgeRestriction().forEach(b -> System.out.println(b.getTitle()));
    }

    private void exerciseTwo() {
        this.bookService.getAllByCopiesLessThan5k().forEach(book ->
        {
            if (book.getEditionType().equals(EditionType.GOLD)) {
                System.out.println(book.getTitle());
            }
        });
    }

    private void exerciseThree() {
        this.bookService.getAllByPrice().forEach(book -> System.out.println(book.getTitle() + " - $" + book.getPrice()));
    }

    private void exerciseFour() {
        this.bookService.getAllByReleaseDateNotInYear().forEach(book -> System.out.println(book.getTitle()));
    }

    private void exerciseFive() {
        this.bookService.getAllByReleaseDateBefore().forEach(book -> System.out.printf("%s %s %.2f%n", book.getTitle(), book.getEditionType(), book.getPrice()));
    }

    private void exerciseSix() {
        this.authorService.getAllAuthorsByEndingString()
                .forEach(author -> System.out.println(author.getFirstName() + " " + author.getLastName()));
    }

    private void exerciseSeven() {
        this.bookService.getAllByTitleContaining().forEach(book -> System.out.println(book.getTitle()));
    }

    private void exerciseEight() {
        for (Map.Entry<Author, List<Book>> entry : this.bookService.getAllByAuthorBooksForExercise8().entrySet()) {
            Author author = entry.getKey();
            List<Book> books = entry.getValue();
            books.forEach(book -> System.out.printf("%s (%s %s)%n", book.getTitle(), author.getFirstName(), author.getLastName()));
        }
    }

    private void exerciseNine() {
        System.out.println("Enter a number:");
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        System.out.printf("!!!!!%nThere are %d books with longer title than %d symbols%n!!!!!%n", this.bookService.getAllBooksCountByTitleLongerThan(num), num);
    }

    private void exerciseTen() {
        LinkedHashMap<Author, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Author, List<Book>> entry : this.bookService.getAllByAuthorBooksForExercise10().entrySet()) {
            Author author = entry.getKey();
            List<Book> books = entry.getValue();
            int copies = books.stream().mapToInt(Book::getCopies).sum();
            result.put(author, copies);
        }
        result.entrySet().stream()
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .forEach(e -> System.out.printf("%s %s - %d%n", e.getKey().getFirstName(), e.getKey().getLastName(), e.getValue()));
    }

    private void exerciseEleven() {
        System.out.println("Enter a Book name:");
        Scanner scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        Book book = this.bookService.getBookInfoByTitle(title);
        System.out.printf("%s %s %s %.2f%n",book.getTitle(),book.getEditionType(),book.getAgeRestriction(),book.getPrice());
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
