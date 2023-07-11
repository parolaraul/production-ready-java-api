package com.parolaraul.recipeapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.parolaraul.recipeapi.repository"})
@EnableTransactionManagement
public class DatabaseConfiguration {
}
