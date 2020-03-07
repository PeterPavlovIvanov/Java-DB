package dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

public interface LabelRepository extends JpaRepository<Label,Long> {
}
