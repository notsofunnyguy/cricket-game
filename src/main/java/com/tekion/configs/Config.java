package com.tekion.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl( "jdbc:mysql://localhost:3306/cricket?useSSL=false" );
        dataSource.setUsername( "root" );
        dataSource.setPassword( "@Saurabh1" );
        return dataSource;
    }
}
