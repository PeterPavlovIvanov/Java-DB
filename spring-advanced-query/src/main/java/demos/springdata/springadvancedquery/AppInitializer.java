package demos.springdata.springadvancedquery;

import dao.IngredientRepository;
import dao.LabelRepository;
import dao.ShampooRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan({"dao.delivery.request"})
@EntityScan("com.delivery.domain")
@EnableJpaRepositories("com.delivery.repository")
public class AppInitializer implements ApplicationRunner {
private final ShampooRepository shampooRepo;
   private final LabelRepository labelRepo;
    private final IngredientRepository ingredientRepo;

    public AppInitializer(ShampooRepository shampooRepo, LabelRepository labelRepo, IngredientRepository ingredientRepo) {
        this.shampooRepo = shampooRepo;
        this.labelRepo = labelRepo;
        this.ingredientRepo = ingredientRepo;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
