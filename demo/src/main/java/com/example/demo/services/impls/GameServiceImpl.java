package com.example.demo.services.impls;

import com.example.demo.dtos.GameAddDto;
import com.example.demo.entities.Game;
import com.example.demo.entities.Role;
import com.example.demo.repositories.GameRepository;
import com.example.demo.services.GameService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final UserServiceImpl userServiceImpl;
    private final ValidationUtil validationUtil;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, UserServiceImpl userServiceImpl, ValidationUtil validationUtil) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.userServiceImpl = userServiceImpl;
        this.validationUtil = validationUtil;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {
        if (this.userServiceImpl.getUserDto().getRole() == Role.ADMIN) {
            Game game = this.modelMapper.map(gameAddDto, Game.class);
            this.gameRepository.saveAndFlush(game);
        } else {
            System.out.println("You must be and ADMIN to do this.");
        }
    }

    @Override
    public void editGame(String[] input) {
        if (this.userServiceImpl.getUserDto() == null) {
            System.out.println("You are not logged in.");
            return;
        }
        if (this.userServiceImpl.getUserDto().getRole() != Role.ADMIN) {
            System.out.println("You must be and ADMIN to do this.");
            return;
        }

        Game game = this.gameRepository.findById(Integer.parseInt(input[1]));
        if (game == null) {
            System.out.println("Invalid game id.");
            return;
        }

        for (int i = 0; i < input.length; i++) {
            String[] items = input[i].split("=");
            switch (items[0]) {
                case "title":
                    game.setTitle(items[1]);
                    break;
                case "price":
                    game.setPrice(new BigDecimal(items[1]));
                    break;
                case "size":
                    game.setSize(Double.parseDouble(items[1]));
                    break;
                case "trailer":
                    game.setTrailer(items[1]);
                    break;
                case "image":
                    game.setImage(items[1]);
                    break;
                case "description":
                    game.setDescription(items[1]);
                    break;
                case "releaseDate":
                    game.setReleaseDate(LocalDate.parse(items[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }
        }

        if (this.validationUtil.isValid(game)) {
            this.gameRepository.saveAndFlush(game);
            System.out.printf("Edited %s.%n", game.getTitle());
        } else {
            this.validationUtil
                    .getViolations(game)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Override
    public void deleteGame(String[] input) {
        if (this.userServiceImpl.getUserDto() == null) {
            System.out.println("You are not logged in.");
            return;
        }
        if (this.userServiceImpl.getUserDto().getRole() != Role.ADMIN) {
            System.out.println("You must be and ADMIN to do this.");
            return;
        }

        Game game = this.gameRepository.findById(Integer.parseInt(input[1]));
        if (game == null) {
            System.out.println("Invalid game id.");
            return;
        }

        this.gameRepository.deleteById((long) Integer.parseInt(input[1]));
        System.out.printf("Deleted %s.%n", game.getTitle());
    }

    @Override
    public List<Game> getAllGames() {
        return this.gameRepository.getAllGames();
    }

    @Override
    public Game getByTitle(String title) {
        return this.gameRepository.findByTitle(title);
    }

}
