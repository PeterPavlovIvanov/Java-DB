package demos.springdata.restdemo.repositories;

import demos.springdata.restdemo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findById(long id);

    @Query("SELECT c FROM Category AS c ORDER BY c.products.size ASC")
    List<Category> findAllOrderByProductsSize();
}
