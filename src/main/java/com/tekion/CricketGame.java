package com.tekion;

import com.tekion.controllers.GameController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.sql.SQLException;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableSwagger2
public class CricketGame {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/cricket";
    public static final String USER = "root";
    public static final String PASS = "@Saurabh1";

    /*
    24-02-2022

    This method main is the entry point
    for our game.


    @param args String[]
     */
    public static void main(String[] args) throws SQLException {

        SpringApplication.run(CricketGame.class, args);

    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.tekion")).build();
    }

}
