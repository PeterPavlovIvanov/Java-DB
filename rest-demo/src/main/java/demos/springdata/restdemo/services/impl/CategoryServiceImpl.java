package demos.springdata.restdemo.services.impl;

import demos.springdata.restdemo.dtos.CategorySeedDto;
import demos.springdata.restdemo.dtos.thirdEx.CategoryThirdExDto;
import demos.springdata.restdemo.entities.Category;
import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.repositories.CategoryRepository;
import demos.springdata.restdemo.services.CategoryService;
import demos.springdata.restdemo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        if (this.categoryRepository.count() != 0) {
            return;
        }
        Arrays.stream(categorySeedDtos)
                .forEach(categorySeedDto -> {
                    if (this.validationUtil.isValid(categorySeedDto)) {
                        Category category = this.modelMapper.map(categorySeedDto, Category.class);
                        this.categoryRepository.saveAndFlush(category);
                    } else {
                        this.validationUtil.violations(categorySeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public Category getById(long id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public List<CategoryThirdExDto> getAllCategoriesThirdEx() {
        List<Category> categories = this.categoryRepository.findAllOrderByProductsSize();
        List<CategoryThirdExDto> categoryThirdExDtoList = new ArrayList<>();

        for (Category category : categories) {
            CategoryThirdExDto categoryThirdExDto = new CategoryThirdExDto();

            categoryThirdExDto.setCategory(category.getName());
            categoryThirdExDto.setProductsCount(category.getProducts().size());

            BigDecimal total = new BigDecimal(0);
            BigDecimal count = new BigDecimal(0);
            for (Product product : category.getProducts()) {
                total = total.add(product.getPrice());
                count = count.add(new BigDecimal(1));
            }
            categoryThirdExDto.setTotalRevenue(total);
            categoryThirdExDto.setAveragePrice(total.divide(count, 2, RoundingMode.HALF_UP));

            categoryThirdExDtoList.add(categoryThirdExDto);
        }

        return categoryThirdExDtoList;
    }
}
