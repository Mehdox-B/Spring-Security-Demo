package com.mehdox.customerthymleaffront.security;


import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private ClientRegistrationRepository clientRegistrationRepository;
    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository){
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(Customizer.withDefaults())
                .authorizeRequests(ar-> ar.requestMatchers("/","/oauth2Login/**","/webjars/**","/h2-console/**").permitAll())
                .authorizeRequests(ar->ar.anyRequest().authenticated())
                .headers((headers)->headers.frameOptions((frameOptionsConfig -> frameOptionsConfig.disable())))
                .csrf((csrf)->csrf.ignoringRequestMatchers("/h2-console/**"))
                //.oauth2Login(Customizer.withDefaults())
                //----Personalization of Login Page------//
                .oauth2Login((oauthLogin)->oauthLogin.loginPage("/oauth2Login").defaultSuccessUrl("/"))
                .logout((logout)->
                        logout.logoutSuccessHandler(oidcLogoutSuccessfulHandler())
                                .logoutSuccessUrl("/").permitAll()
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling((exception)-> exception.accessDeniedPage("/NotAuthorized"))
                .build();
    }
    private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessfulHandler(){
        final OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessfulHandler =
                new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessfulHandler.setPostLogoutRedirectUri("http://localhost:9090/?logoutsuccess=true");
        return oidcLogoutSuccessfulHandler;
    }
    //------Mapping The keycloak Roles to Spring Security Authorities(GrantAuthority) Interface-----//
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach((authority) -> {
                if (authority instanceof OidcUserAuthority oidcAuth) {
                    mappedAuthorities.addAll(mapAuthorities(oidcAuth.getIdToken().getClaims()));
                    System.out.println(oidcAuth.getAttributes());
                } else if (authority instanceof OAuth2UserAuthority oauth2Auth) {
                    mappedAuthorities.addAll(mapAuthorities(oauth2Auth.getAttributes()));
                    System.out.println(oauth2Auth.getAttributes());
                }
            });
            return mappedAuthorities;
        };
    }
    private List<SimpleGrantedAuthority> mapAuthorities(final Map<String, Object> attributes) {
        final Map<String, Object> realmAccess = ((Map<String, Object>)attributes.getOrDefault("realm_access", Collections.emptyMap()));
        final Collection<String> roles = ((Collection<String>)realmAccess.getOrDefault("roles", Collections.emptyList()));
        return roles.stream()
                .map((role) -> new SimpleGrantedAuthority(role))
                .toList();
    }
}
