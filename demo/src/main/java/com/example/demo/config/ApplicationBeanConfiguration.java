package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.demo.utils.ValidationUtil;
import com.example.demo.utils.ValidationUtilImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

    @Bean
    public BufferedReader bufferedReader(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
