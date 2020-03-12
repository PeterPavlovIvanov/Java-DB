package com.example.demo.services.impls;

import com.example.demo.dtos.UserDto;
import com.example.demo.dtos.UserLoginDto;
import com.example.demo.dtos.UserRegisterDto;
import com.example.demo.entities.Game;
import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private UserDto userDto;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        User user = this.modelMapper.map(userRegisterDto, User.class);
        user.setRole(this.userRepository.count() == 0 ? Role.ADMIN : Role.USER);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        User user = this.userRepository
                .findByEmail(userLoginDto.getEmail());
        if (user == null || !(user.getPassword().equals(userLoginDto.getPassword()))) {
            System.out.println("Incorrect username / password.");
        } else {
            this.userDto = this.modelMapper.map(user, UserDto.class);
            System.out.println("Successfully logged in " + user.getFullName());
        }
    }

    @Override
    public void logoutUser() {
        if (this.userDto == null || this.userDto.getFullName() == null) {
            System.out.println("Cannot log out. No user was logged in.");
        } else {
            System.out.printf("User %s successfully logged out.%n", userDto.getFullName());
            this.userDto = null;
        }
    }

    public UserDto getUserDto() {
        return userDto;
    }

    @Override
    public void purchaseGame(User user, Game game) {
        user.getGames().add(game);
        this.userRepository.saveAndFlush(user);
        System.out.println("You bought " + game.getTitle());
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<Game> ownedGames(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user.getGames().isEmpty()) {
            System.out.println("You haven't got any purchased games yet.");
            return null;
        }
        return user.getGames();
    }
}
