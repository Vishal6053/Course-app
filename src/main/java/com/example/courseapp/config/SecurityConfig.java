package com.example.courseapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/api/auth/register", "/api/auth/verify-otp", "/api/auth/login").permitAll()
                .requestMatchers("/api/auth/courses/filter").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/courses").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/courses").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/auth/courses/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/auth/courses/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/users/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/api/addresses/filter").permitAll()
                .requestMatchers("/api/auth/addresses/**").permitAll()
                .requestMatchers("/api/auth/enrollments/**").permitAll()
                .requestMatchers("/api/auth/users/enrolled/**").permitAll()
                .requestMatchers("/api/auth/users/enroll").permitAll()

                // Admin-specific actions
                .requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}
