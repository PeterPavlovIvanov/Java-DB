package demos.springdata.restdemo.controllers;

import com.google.gson.Gson;
import demos.springdata.restdemo.dtos.*;
import demos.springdata.restdemo.dtos.firstEx.ProductFirstExDto;
import demos.springdata.restdemo.dtos.fourthEx.UPDto;
import demos.springdata.restdemo.dtos.fourthEx.UserFourthExDto;
import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.services.CategoryService;
import demos.springdata.restdemo.services.ProductService;
import demos.springdata.restdemo.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class AppController implements CommandLineRunner {

    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;

    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        //2. Seed the Database
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();

        //3. Query and Export Data

//        this.exercise1();//Query 1 – Products in Range
//
//
//        this.exercise2();//Query 2 – Successfully Sold Products
//
//
//        this.exercise3();//Query 3 – Categories by Products Count
//
//
//        this.exercise4();//Query 4 – Users and Products

    }

    private void exercise1() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter lower range: ");
        BigDecimal lower = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));
        System.out.println("Enter higher range: ");
        BigDecimal higher = BigDecimal.valueOf(Double.parseDouble(scanner.nextLine()));

        List<Product> allBetween = this.productService.getAllBetween(lower, higher);

        List<String> result = new ArrayList<>();

        for (Product product : allBetween) {
            ProductFirstExDto productFirstExDto =
                    new ProductFirstExDto(product.getName(), product.getPrice(), product.getSeller());

            result.add(this.gson.toJson(productFirstExDto));
        }
        System.out.println(result);
    }

    private void exercise2() {
        System.out.println(this.gson.toJson(this.userService.findAllSecondExercise()));
    }

    private void exercise3() {
        System.out.println(this.gson.toJson(this.categoryService.getAllCategoriesThirdEx()));
    }

    private void exercise4() {
        List<UserFourthExDto> users = this.userService.findAllWithSoldProductsFourthExercise();

        UPDto upDto = new UPDto(users.size(), users);

        System.out.println(this.gson.toJson(upDto));
    }

    private void seedProducts() throws FileNotFoundException {
        ProductSeedDto[] productSeedDtos = this.gson
                .fromJson(new FileReader("src/main/resources/files/products.json")
                        , ProductSeedDto[].class);

        this.productService.seedProducts(productSeedDtos);
    }

    private void seedUsers() throws IOException {
        UserSeedDto[] userSeedDtos = this.gson
                .fromJson(new FileReader("src/main/resources/files/users.json")
                        , UserSeedDto[].class);

        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategories() throws FileNotFoundException {
        CategorySeedDto[] categorySeedDtos = this.gson
                .fromJson(new FileReader("src/main/resources/files/categories.json")
                        , CategorySeedDto[].class);

        this.categoryService.seedCategories(categorySeedDtos);
    }
}
