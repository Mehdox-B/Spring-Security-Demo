package com.mehdox.invetoryservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests((ar)->ar.anyRequest().authenticated())
                //---Authorization/Authentication Require Jwt Token---//
                .oauth2ResourceServer((oauth2)->oauth2.jwt((jwt)->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .headers((headers)->headers.frameOptions((frameOptionsConfig -> frameOptionsConfig.disable())))
                .csrf((csrf)->csrf.ignoringRequestMatchers("/h2-console/**"))
                .build();
    }
}
