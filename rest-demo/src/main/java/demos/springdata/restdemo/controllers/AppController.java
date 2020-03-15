package demos.springdata.restdemo.controllers;

import com.google.gson.Gson;
import demos.springdata.restdemo.dtos.*;
import demos.springdata.restdemo.dtos.firstEx.ProductFirstExDto;
import demos.springdata.restdemo.dtos.firstEx.SellerDto;
import demos.springdata.restdemo.dtos.fourthEx.UPDto;
import demos.springdata.restdemo.dtos.fourthEx.UserFourthExDto;
import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.services.CategoryService;
import demos.springdata.restdemo.services.ProductService;
import demos.springdata.restdemo.services.UserService;
import demos.springdata.restdemo.utils.FileIOUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class AppController implements CommandLineRunner {

    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FileIOUtil fileIOUtil;

    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService, FileIOUtil fileIOUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileIOUtil = fileIOUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        //2. Seed the Database
        this.seedUsers();
        this.seedCategories();
        this.seedProducts();

        //3. Query and Export Data

//       this.exercise1();//Query 1 – Products in Range
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

    private void exercise1() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter lower range: ");
        BigDecimal lower = BigDecimal.valueOf(Double.parseDouble(bufferedReader.readLine()));
        System.out.println("Enter higher range: ");
        BigDecimal higher = BigDecimal.valueOf(Double.parseDouble(bufferedReader.readLine()));

        List<Product> allBetween = this.productService.getAllBetween(lower, higher);

        List<ProductFirstExDto> result = new ArrayList<>();

        for (Product product : allBetween) {
            SellerDto sellerDto = new SellerDto(product.getSeller().getFirstName() + product.getSeller().getLastName());
            ProductFirstExDto productFirstExDto =
                    new ProductFirstExDto(product.getName(), product.getPrice(), sellerDto);
            result.add(productFirstExDto);
        }

        this.fileIOUtil.write(this.gson.toJson(result), "src/main/resources/outputs/output1.json");
        System.out.println(this.gson.toJson(result));
    }

    private void exercise2() throws IOException {
        this.fileIOUtil.write(this.gson.toJson(this.userService.findAllSecondExercise()), "src/main/resources/outputs/output2.json");
        System.out.println(this.gson.toJson(this.userService.findAllSecondExercise()));
    }

    private void exercise3() throws IOException {
        this.fileIOUtil.write(this.gson.toJson(this.categoryService.getAllCategoriesThirdEx()), "src/main/resources/outputs/output3.json");
        System.out.println(this.gson.toJson(this.categoryService.getAllCategoriesThirdEx()));
    }

    private void exercise4() throws IOException {
        List<UserFourthExDto> users = this.userService.findAllWithSoldProductsFourthExercise();

        UPDto upDto = new UPDto(users.size(), users);

        this.fileIOUtil.write(this.gson.toJson(upDto), "src/main/resources/outputs/output4.json");
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
