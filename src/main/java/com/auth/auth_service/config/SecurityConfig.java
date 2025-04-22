package com.auth.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//    @Bean(name = "customSecurityFilterChain")
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors() .and()
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/user/login", "/api/user/register").permitAll()
//                        .anyRequest().authenticated())
//                .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
@Bean(name = "customSecurityFilterChain")
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().cors()
            .and()
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/user/login", "/api/user/register").permitAll()
                    .requestMatchers("/api/restaurant/**").hasRole("RESTAURANT_ADMIN")
                    .requestMatchers("/api/delivery/**").hasRole("DELIVERY_PERSONNEL")
                    .requestMatchers("/api/customer/**").hasRole("CUSTOMER")
                    .anyRequest().authenticated())
            .addFilterBefore(new JwtTokenValidator(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}