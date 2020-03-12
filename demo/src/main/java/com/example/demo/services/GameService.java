package com.example.demo.services;

import com.example.demo.dtos.GameAddDto;
import com.example.demo.entities.Game;

import java.util.List;

public interface GameService {
    void addGame(GameAddDto gameAddDto);
    void editGame(String[] input);
    void deleteGame(String[] input);
    List<Game> getAllGames();
    Game getByTitle(String title);

}
