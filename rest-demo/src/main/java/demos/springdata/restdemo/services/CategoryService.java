package demos.springdata.restdemo.services;

import demos.springdata.restdemo.dtos.CategorySeedDto;
import demos.springdata.restdemo.dtos.thirdEx.CategoryThirdExDto;
import demos.springdata.restdemo.entities.Category;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    Category getById(long id);

    List<CategoryThirdExDto> getAllCategoriesThirdEx();
}
