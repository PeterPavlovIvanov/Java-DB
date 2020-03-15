package demos.springdata.restdemo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import demos.springdata.restdemo.utils.FileIOUtil;
import demos.springdata.restdemo.utils.FileIOUtilImpl;
import demos.springdata.restdemo.utils.ValidationUtil;
import demos.springdata.restdemo.utils.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }

    @Bean
    public ValidationUtil validationUtil() {
        return new ValidationUtilImpl();
    }

    @Bean
    public FileIOUtil fileIOUtil() {
        return new FileIOUtilImpl();
    }
}
