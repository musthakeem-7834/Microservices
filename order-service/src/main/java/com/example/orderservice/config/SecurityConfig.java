package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // H2 Console
                        .requestMatchers(
                                "/h2-console/**"
                        ).permitAll()

                        // Order APIs
                        .requestMatchers(
                                "/api/v1/orders/**"
                        ).hasAnyRole("USER", "MANAGER", "ADMIN")

                        // Everything else
                        .anyRequest().authenticated()
                )

                .headers(headers ->
                        headers.frameOptions(
                                frame -> frame.sameOrigin()
                        )
                );

        return http.build();
    }
}