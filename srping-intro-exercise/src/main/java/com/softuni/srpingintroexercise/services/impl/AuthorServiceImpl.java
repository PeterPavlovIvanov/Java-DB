package com.softuni.srpingintroexercise.services.impl;

import com.softuni.srpingintroexercise.constants.GlobalConstants;
import com.softuni.srpingintroexercise.entities.Author;
import com.softuni.srpingintroexercise.repositories.AuthorRepository;
import com.softuni.srpingintroexercise.services.AuthorService;
import com.softuni.srpingintroexercise.utils.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] fileContent = this.fileUtil.readFileContent(GlobalConstants.AUTHOR_FILE_PATH);

        Arrays.stream(fileContent).forEach(r -> {
            String[] params = r.split("\\s+");
            Author author = new Author(params[0], params[1]);

            this.authorRepository.saveAndFlush(author);
        });
    }

    @Override
    public int getAllAuthorsCount() {
        return (int) this.authorRepository.count();
    }

    @Override
    public Author findAuthorById(long id) {
        return this.authorRepository.getOne(id);
    }

    @Override
    public List<Author> getAllAuthorsByBooksCount() {
        return this.authorRepository.getAllAuthorsByBooksCount();
    }

    @Override
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAllAuthors();
    }

}
