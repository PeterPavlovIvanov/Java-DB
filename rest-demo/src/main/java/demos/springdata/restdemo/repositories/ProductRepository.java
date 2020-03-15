package demos.springdata.restdemo.repositories;

import demos.springdata.restdemo.entities.Product;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product AS p WHERE p.price > :lower AND p.price < :higher AND p.buyer IS NULL")
    List<Product> findAllByPriceBetweenOrderByPrice(@Param(value = "lower") BigDecimal lower,@Param(value = "higher") BigDecimal higher);
}
