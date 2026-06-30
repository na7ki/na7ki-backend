package com.na7ki.backend.core.security;

import com.na7ki.backend.core.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;





    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        // to be populated when endpoints are created
        // request matchers run as the final security filter, after the jwt and the default Spring filter, respectively
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()

                        .requestMatchers("/api/specialist/**").hasAuthority("SPECIALIST")
                        .requestMatchers("/api/exercises/items", "/api/exercises/items/**").hasAuthority("PATIENT")

                        .anyRequest().authenticated()
        )

        //registers the custom authentication provider that takes into account the password encryptor and the custom User Details Service. Without it Spring uses its default, which isn't customized
        .authenticationProvider(authenticationProvider())

        //use JWT filter before the default Spring filter "UsernamePasswordAuthenticationFilter". Eventually, this entirely replaces this default filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

        //turn off session creation. Because we're using JWT
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        //disable csrf because we're using JWT not cookies. CSRF may only happen to cookies
        .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}