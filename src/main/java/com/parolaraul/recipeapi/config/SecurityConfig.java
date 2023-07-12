package com.parolaraul.recipeapi.config;

import com.parolaraul.recipeapi.config.web.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(withDefaults())
                .sessionManagement(withDefaults())
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults())
                .exceptionHandling(withDefaults());
        return http.build();
    }
}
