package com.parolaraul.recipeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MariaDBContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestRecipeapiApplication {

    @Bean
    @ServiceConnection
    MariaDBContainer<?> mariaDbContainer() {
        return new MariaDBContainer<>("mariadb:latest");
    }

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestRecipeapiApplication.class).run(args);
    }

}
