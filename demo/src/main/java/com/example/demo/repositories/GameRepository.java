package com.example.demo.repositories;

import com.example.demo.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT g FROM Game AS g WHERE g.id = :param")
    Game findById(@Param(value = "param") long id);

    @Query("SELECT g FROM Game AS g")
    List<Game> getAllGames();

    Game findByTitle(String title);
}
