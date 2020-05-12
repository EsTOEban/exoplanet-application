package com.example.exoplanet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SpringFoxConfig {

    @Bean
    public Docket dataPopulationApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("data")
            .select()
            .paths(PathSelectors.ant("/data/**"))
            .build();
    }
}
