package com.example.emotion_analysis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity

 */
public class WebSecurityConfig {
/*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails administrator = User.builder()
                .username("admin")
                .password("123")
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("123")
                .roles("PATIENT")
                .build();

        return new InMemoryUserDetailsManager(administrator, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/medicalDiagnosis"))  // Disable CSRF for specific POST endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers("/medicalDiagnosis").permitAll()  // Επιτρέπει πρόσβαση σε όλους στη διαδρομή /medicalDiagnosis
                        .requestMatchers(HttpMethod.GET, "/patients").hasRole("ADMIN")  // Only ADMIN can access /patients with GET
                        .requestMatchers(HttpMethod.GET, "/locations/**").hasRole("ADMIN")  // Only ADMIN can access /patients with GET
                        .requestMatchers(HttpMethod.GET, "/patients/**").hasRole("ADMIN")  // Only ADMIN can access all other GET requests under /patients
                        .requestMatchers(HttpMethod.GET, "/psychologists/**").hasAnyRole("ADMIN", "USER")  // Allow all roles for psychologist GET requests
                        .requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")   // Only ADMIN can POST
                        .requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")    // Only ADMIN can PUT
                        .requestMatchers(HttpMethod.DELETE, "/patients/delete/id/*").permitAll() // Only ADMIN can DELETE
                        .requestMatchers("/public/**").permitAll()  // Allow public endpoints without authentication
           DELETE '/' IN : *(/)/*.html")             .requestMatchers("/**//*.html").permitAll()
                        .anyRequest().authenticated()  // All other endpoints require authentication
                )
                .formLogin(login -> login
                        .permitAll()  // Allow access to login page for all users
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Define allowed origins, methods, headers, and credentials
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://192.168.1.203:8080"));  // Allow localhost and Android device IP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Apply this configuration to all endpoints
        return source;
    }
    */
}

