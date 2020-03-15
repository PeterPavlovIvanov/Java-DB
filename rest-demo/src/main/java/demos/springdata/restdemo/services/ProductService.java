package demos.springdata.restdemo.services;

import demos.springdata.restdemo.dtos.ProductSeedDto;
import demos.springdata.restdemo.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDto[] products);
    List<Product> getAllBetween(BigDecimal lower,BigDecimal higher);
}
