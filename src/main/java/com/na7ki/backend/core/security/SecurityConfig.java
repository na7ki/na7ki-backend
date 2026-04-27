package com.na7ki.backend.core.security;

import com.na7ki.backend.core.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        // to be populated when endpoints are created
        // request matchers run as the final security filter, after the jwt and the default Spring filter, respectively
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
//                        .requestMatchers(HttpMethod.GET, "/api/test/**").hasAuthority("ADMIN")
        );



        //use JWT filter before the default Spring filter "UsernamePasswordAuthenticationFilter". Eventually, this entirely replaces this default filter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        //turn off session creation. Because we're using JWT
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //disable csrf because we're using JWT not cookies. CSRF may only happen to cookies
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
