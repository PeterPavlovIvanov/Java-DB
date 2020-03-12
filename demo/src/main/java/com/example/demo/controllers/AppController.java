package com.example.demo.controllers;

import com.example.demo.dtos.GameAddDto;
import com.example.demo.dtos.UserLoginDto;
import com.example.demo.dtos.UserRegisterDto;
import com.example.demo.entities.Game;
import com.example.demo.entities.User;
import com.example.demo.services.impls.GameServiceImpl;
import com.example.demo.services.impls.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.services.UserService;
import com.example.demo.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AppController implements CommandLineRunner {

    private final BufferedReader bufferedReader;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final GameServiceImpl gameServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AppController(BufferedReader bufferedReader, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService, GameServiceImpl gameServiceImpl, UserServiceImpl userServiceImpl) {
        this.bufferedReader = bufferedReader;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.gameServiceImpl = gameServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter commands");
            String[] input = this.bufferedReader.readLine().split("\\|");

            switch (input[0]) {
                case "RegisterUser":
                    if (!input[2].equals(input[3])) {
                        System.out.println("Passwords don't match.");
                    }
                    UserRegisterDto userRegisterDto = new UserRegisterDto(input[1], input[2], input[4]);

                    if (this.validationUtil.isValid(userRegisterDto)) {
                        this.userService.registerUser(userRegisterDto);
                        System.out.printf("%s was registered.%n", input[4]);
                    } else {
                        this.validationUtil
                                .getViolations(userRegisterDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                    break;
                case "LoginUser":
                    UserLoginDto userLoginDto = new UserLoginDto(input[1], input[2]);
                    this.userService.loginUser(userLoginDto);
                    break;
                case "Logout":
                    this.userService.logoutUser();
                    break;
                case "AddGame":
                    GameAddDto gameAddDto = new GameAddDto(
                            input[1],
                            new BigDecimal(input[2]),
                            Double.parseDouble(input[3]),
                            input[4],
                            input[5],
                            input[6],
                            LocalDate.parse(input[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    if (this.validationUtil.isValid(gameAddDto)) {
                        this.gameServiceImpl.addGame(gameAddDto);
                        System.out.printf("Added %s.%n", input[1]);
                    } else {
                        this.validationUtil
                                .getViolations(gameAddDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                    break;
                case "EditGame":
                    this.gameServiceImpl.editGame(input);
                    break;
                case "DeleteGame":
                    this.gameServiceImpl.deleteGame(input);
                    break;
                case "AllGames":
                    if (this.gameServiceImpl.getAllGames().isEmpty()) {
                        System.out.println("There is no games in the store.");
                    } else {
                        this.gameServiceImpl.getAllGames().forEach(game -> System.out.printf("%s %.2f%n", game.getTitle(), game.getPrice()));
                    }
                    break;
                case "DetailGame":
                    Game g = this.gameServiceImpl.getByTitle(input[1]);
                    if (g == null) {
                        System.out.println("Invalid game name.");
                    } else {
                        System.out.printf("Title: %s%nPrice: %.2f %nDescription: %s %nRelease date: %s%n",
                                g.getTitle(), g.getPrice(), g.getDescription(), g.getReleaseDate());
                    }
                    break;
                case "Purchase":
                    String email = this.userServiceImpl.getUserDto().getEmail();
                    User user = this.userServiceImpl.getByEmail(email);
                    Game game = this.gameServiceImpl.getByTitle(input[1]);

                    if (game == null) {
                        System.out.println("Invalid game name.");
                    } else {
                        this.userService.purchaseGame(user, game);
                        System.out.printf("%s purchased successfully.%n", game.getTitle());
                    }
                    break;
                case "OwnedGames":
                    String mail = this.userServiceImpl.getUserDto().getEmail();
                    if(this.userService.ownedGames(mail) != null){
                        this.userService.ownedGames(mail).forEach(g1 -> System.out.printf("%s%n", g1.getTitle()));
                    }
                    break;
            }

        }

    }
}
