package com.example.xmlexercise.config;


import com.example.xmlexercise.utils.ValidationUtil;
import com.example.xmlexercise.utils.ValidationUtilImpl;
import com.example.xmlexercise.utils.XmlParser;
import com.example.xmlexercise.utils.XmlParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }


    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Random random(){
        return  new Random();
    }
}
