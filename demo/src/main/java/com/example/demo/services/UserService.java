package com.example.demo.services;

import com.example.demo.dtos.UserLoginDto;
import com.example.demo.dtos.UserRegisterDto;
import com.example.demo.entities.Game;
import com.example.demo.entities.User;

import java.util.List;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logoutUser();

    void purchaseGame(User user, Game game);

    User getByEmail(String email);

    List<Game> ownedGames(String email);
}

