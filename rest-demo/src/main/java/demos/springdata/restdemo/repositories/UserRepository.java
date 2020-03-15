package demos.springdata.restdemo.repositories;

import demos.springdata.restdemo.entities.Product;
import demos.springdata.restdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    List<User> findAll();

    @Query("SELECT u FROM User AS u WHERE u.sold.size > 0 ORDER BY u.lastName ASC, u.firstName ASC")
    List<User> findAllWithSoldItems();

    @Query("SELECT u FROM User AS u WHERE u.sold.size > 0 ORDER BY u.sold.size DESC, u.lastName ASC")
    List<User> findAllWithSoldItemsExFour();

}
