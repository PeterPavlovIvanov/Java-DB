package dao;

import entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

}
