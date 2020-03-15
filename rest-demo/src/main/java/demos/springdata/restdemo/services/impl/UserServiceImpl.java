package demos.springdata.restdemo.services.impl;

import demos.springdata.restdemo.dtos.fourthEx.ProductFourthExDto;
import demos.springdata.restdemo.dtos.fourthEx.SoldProductsDto;
import demos.springdata.restdemo.dtos.fourthEx.UserFourthExDto;
import demos.springdata.restdemo.dtos.secondEx.ProductSecondExDto;
import demos.springdata.restdemo.dtos.secondEx.UserSecondExDto;
import demos.springdata.restdemo.dtos.UserSeedDto;
import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.entities.User;
import demos.springdata.restdemo.repositories.UserRepository;
import demos.springdata.restdemo.services.UserService;
import demos.springdata.restdemo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) throws IOException {
        if (this.userRepository.count() != 0) {
            return;
        }

        Arrays.stream(userSeedDtos)
                .forEach(userSeedDto -> {
                    if (this.validationUtil.isValid(userSeedDto)) {
                        User user = this.modelMapper.map(userSeedDto, User.class);
                        this.userRepository.saveAndFlush(user);
                    } else {
                        this.validationUtil.violations(userSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public User getById(long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public int getAllCount() {
        return this.userRepository.findAll().size();
    }

    @Override
    public List<UserSecondExDto> findAllSecondExercise() {
        List<User> users = this.userRepository.findAllWithSoldItems();
        List<UserSecondExDto> usersSecondExDtoList = new ArrayList<>();

        for (User user : users) {
            Set<ProductSecondExDto> productSecondExDtos = user.getSold()
                    .stream()
                    .filter(product -> product.getBuyer() != null)
                    .map(product -> this.modelMapper.map(product, ProductSecondExDto.class))
                    .collect(Collectors.toSet());

            UserSecondExDto userSecondExDto =
                    new UserSecondExDto(user.getFirstName(), user.getLastName(), productSecondExDtos);

            usersSecondExDtoList.add(userSecondExDto);
        }

        return usersSecondExDtoList;
    }

    @Override
    public List<UserFourthExDto> findAllWithSoldProductsFourthExercise() {
        List<User> users = this.userRepository.findAllWithSoldItemsExFour();
        List<UserFourthExDto> userFourthExDtoList = new ArrayList<>();

        for (User user : users) {
            Set<ProductFourthExDto> productFourthExDtoSet = new HashSet<>();
            for (Product product : user.getSold()) {
                ProductFourthExDto productFourthExDto = this.modelMapper.map(product, ProductFourthExDto.class);
                productFourthExDtoSet.add(productFourthExDto);
            }

            SoldProductsDto soldProductsDto =
                    new SoldProductsDto(productFourthExDtoSet.size(), productFourthExDtoSet);

            UserFourthExDto userFourthExDto = new UserFourthExDto(user.getFirstName(),
                    user.getLastName(), user.getAge(), soldProductsDto);

            userFourthExDtoList.add(userFourthExDto);
        }

        return userFourthExDtoList;
    }

}
