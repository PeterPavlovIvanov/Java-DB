package demos.springdata.restdemo.services.impl;

import demos.springdata.restdemo.dtos.ProductSeedDto;
import demos.springdata.restdemo.entities.Category;
import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.entities.User;
import demos.springdata.restdemo.repositories.ProductRepository;
import demos.springdata.restdemo.services.CategoryService;
import demos.springdata.restdemo.services.ProductService;
import demos.springdata.restdemo.services.UserService;
import demos.springdata.restdemo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ValidationUtil validationUtil, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public void seedProducts(ProductSeedDto[] productsSeedDtos) {
        if (this.productRepository.count() != 0) {
            return;
        }
        User seller;
        User buyer;
        for (ProductSeedDto productSeedDto : productsSeedDtos) {
            int isBought = 1;
            if (new Random().nextInt(5) != 2 && new Random().nextInt(5) != 3) {
                isBought = 0;
            }
            if (!this.validationUtil.isValid(productSeedDto)) {
                this.validationUtil.violations(productSeedDto)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
                break;
            }

            Random randomSeller = new Random();
            Random randomBuyer = new Random();

            int rnSellerId = randomSeller.nextInt(this.userService.getAllCount()) + 1;
            seller = this.userService.getById(rnSellerId);

            if(isBought == 1){
                int rnBuyerId = randomBuyer.nextInt(this.userService.getAllCount()) + 1;
                buyer = this.userService.getById(rnBuyerId);
            }else{
                buyer = null;
            }

            productSeedDto.setBuyer(buyer);
            productSeedDto.setSeller(seller);

            int randomCategoriesCount = new Random().nextInt(11) + 1;
            while (randomCategoriesCount-- >= 1) {
                Category category = this.categoryService
                        .getById(new Random().nextInt(11) + 1);
                if (productSeedDto.getCategories() == null) {
                    Set<Category> temp = new HashSet<>();
                    temp.add(category);
                    productSeedDto.setCategories(temp);
                } else {
                    productSeedDto.getCategories().add(category);
                }
            }

            Product productToAdd = this.modelMapper.map(productSeedDto, Product.class);

            this.productRepository.save(productToAdd);
        }
    }

    @Override
    public List<Product> getAllBetween(BigDecimal lower, BigDecimal higher) {
        return this.productRepository.findAllByPriceBetweenOrderByPrice(lower, higher);
    }
}
