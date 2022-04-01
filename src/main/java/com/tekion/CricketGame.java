package com.tekion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.sql.SQLException;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class CricketGame {

    /*
    24-02-2022

    This method main is the entry point
    for our game.


    @param args String[]
     */
    public static void main(String[] args) throws SQLException {

        SpringApplication.run(CricketGame.class, args);

    }


}
