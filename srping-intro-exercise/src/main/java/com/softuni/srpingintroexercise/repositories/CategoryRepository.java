package com.softuni.srpingintroexercise.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.softuni.srpingintroexercise.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
