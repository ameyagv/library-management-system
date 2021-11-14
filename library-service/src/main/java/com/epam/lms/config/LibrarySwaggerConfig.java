package com.epam.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class LibrarySwaggerConfig {
    @Bean
    public Docket libraryServiceApi(){
        return new Docket(DocumentationType.SWAGGER_2).
                select().
                apis(RequestHandlerSelectors.any()).
                paths(PathSelectors.any()).
                build().apiInfo(getApiInfo());
    }

    public ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Library Service")
                .version("1.0")
                .description("API for managing books and users")
                .contact(new Contact("Ameya Vaichalkar","http://localhost:8082","ameya.vaichalkar@gmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }
}
