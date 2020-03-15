package demos.springdata.restdemo.services;

import demos.springdata.restdemo.dtos.fourthEx.UserFourthExDto;
import demos.springdata.restdemo.dtos.secondEx.UserSecondExDto;
import demos.springdata.restdemo.dtos.UserSeedDto;
import demos.springdata.restdemo.entities.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {
    void seedUsers(UserSeedDto[] userSeedDto) throws IOException;
    User getById(long id);
    int getAllCount();
    List<UserSecondExDto> findAllSecondExercise();
    List<UserFourthExDto> findAllWithSoldProductsFourthExercise();
}
