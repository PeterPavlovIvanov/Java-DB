package com.softuni.srpingintroexercise.services;


import com.softuni.srpingintroexercise.entities.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    Category getCategoryById(long id);
}
