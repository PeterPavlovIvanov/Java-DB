package com.softuni.srpingintroexercise.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softuni.srpingintroexercise.constants.GlobalConstants;
import com.softuni.srpingintroexercise.entities.*;
import com.softuni.srpingintroexercise.repositories.AuthorRepository;
import com.softuni.srpingintroexercise.repositories.BookRepository;
import com.softuni.srpingintroexercise.services.AuthorService;
import com.softuni.srpingintroexercise.services.BookService;
import com.softuni.srpingintroexercise.services.CategoryService;
import com.softuni.srpingintroexercise.utils.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileUtil fileUtil;


    @Autowired // not neseccery
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, FileUtil fileUtil, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil.readFileContent(GlobalConstants.BOOKS_FILE_PATH);

        Arrays.stream(fileContent).forEach(row -> {
            String[] params = row.split("\\s+");

            Author author = this.getRandomAuthor();

            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);

            int copies = Integer.parseInt(params[2]);

            BigDecimal price = new BigDecimal(params[3]);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];

            String title = this.getTitle(params);

            Set<Category> categories = this.getRandomCategories();

            Book book = new Book();
            book.setAuthor(author);
            book.setEditionType(editionType);
            book.setReleaseDate(releaseDate);
            book.setCopies(copies);
            book.setPrice(price);
            book.setAgeRestriction(ageRestriction);
            book.setTitle(title);
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        });
    }

    @Override
    public List<Book> getAllBooksAfter2000() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate releaseDate = LocalDate.parse("31/12/2000", formatter);
        return this.bookRepository.findAllByReleaseDateAfter(releaseDate);
    }

    @Override
    public List<Book> getAllByGeorgePowell() {
        return this.bookRepository.findAllByGeorgePowell();
    }

    @Override
    public List<Book> getAllByAgeRestriction() {
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        AgeRestriction ageRestriction;
        if ("teen".equals(type.toLowerCase())) {
            ageRestriction = AgeRestriction.TEEN;
        } else if ("minor".equals(type.toLowerCase())) {
            ageRestriction = AgeRestriction.MINOR;
        } else if ("adult".equals(type.toLowerCase())) {
            ageRestriction = AgeRestriction.ADULT;
        } else {
            ageRestriction = null;
        }
        return this.bookRepository.findAllByAgeRestriction(ageRestriction);
    }

    @Override
    public List<Book> getAllByCopiesLessThan5k() {
        return this.bookRepository.findAllByCopiesLessThan(5000);
    }

    @Override
    public List<Book> getAllByPrice() {
        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(new BigDecimal(5), new BigDecimal(40));
    }

    @Override
    public List<Book> getAllByReleaseDateNotInYear() {
        System.out.println("Enter year:");
        Scanner scanner = new Scanner(System.in);
        int year = Integer.parseInt(scanner.nextLine());

        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
    }

    @Override
    public List<Book> getAllByReleaseDateBefore() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date: (example: 30-12-1989)");

        String[] date = scanner.nextLine().split("-");
        int year = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[0]);
        LocalDate before = LocalDate.of(year, month, day);

        return this.bookRepository.findAllByReleaseDateBefore(before);
    }

    @Override
    public List<Book> getAllByTitleContaining() {
        System.out.println("Enter a string:");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return this.bookRepository.findAllByTitleContaining(str);
    }

    @Override
    public LinkedHashMap<Author, List<Book>> getAllByAuthorBooksForExercise8() {
        List<Author> authorsStartingWith = this.authorService.getAllByLastNameStartingWith();
        LinkedHashMap<Author, List<Book>> result = new LinkedHashMap<>();

        for (Author author : authorsStartingWith) {
            result.put(author, this.bookRepository.findAllByAuthor(author));
        }

        return result;
    }

    @Override
    public LinkedHashMap<Author, List<Book>> getAllByAuthorBooksForExercise10() {
        List<Author> authors = this.authorService.getAllAuthors();
        LinkedHashMap<Author, List<Book>> result = new LinkedHashMap<>();

        for (Author author : authors) {
            result.put(author, this.bookRepository.findAllByAuthor(author));
        }

        return result;
    }

    @Override
    public Book getBookInfoByTitle(String title) {
        for (Book book : this.bookRepository.findBooksInformation()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public int getAllBooksCountByTitleLongerThan(int length) {
        List<String> result = new ArrayList<>();
        for (Book book : this.bookRepository.findAllBy()) {
            if(book.getTitle().length()>length){
                result.add(book.getTitle());
            }
        }
        return result.size();
    }

    private Set<Category> getRandomCategories() {
        Set<Category> result = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(3) + 1;// in mysql there is no id = 0

        for (int i = 1; i <= bound; i++) {
            int categoryId = random.nextInt(8) + 1;
            result.add(this.categoryService.getCategoryById((long) categoryId));
        }
        return result;
    }

    private String getTitle(String[] params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            sb.append(params[i]).append(" ");
        }

        return sb.toString().trim();
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt(this.authorService.getAllAuthorsCount()) + 1;//in mysql there is not id = 0

        return this.authorService.findAuthorById((long) randomId);
    }

}
