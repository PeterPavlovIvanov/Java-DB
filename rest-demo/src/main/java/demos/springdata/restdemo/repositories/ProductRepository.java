package demos.springdata.restdemo.repositories;

import demos.springdata.restdemo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByPriceBetweenOrderByPrice(BigDecimal lower, BigDecimal higher);

}
