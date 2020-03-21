package com.softuni.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softuni.demo.utils.FileIOUtil;
import com.softuni.demo.utils.FileIOUtilImpl;
import com.softuni.demo.utils.ValidationUtil;
import com.softuni.demo.utils.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
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
