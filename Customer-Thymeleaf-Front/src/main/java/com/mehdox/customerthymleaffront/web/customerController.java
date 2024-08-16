package com.mehdox.customerthymleaffront.web;

import com.mehdox.customerthymleaffront.Model.Product;
import com.mehdox.customerthymleaffront.entities.Customer;
import com.mehdox.customerthymleaffront.repository.CustomerRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class customerController {
    private CustomerRepository customerRepository;
    private ClientRegistrationRepository clientRegistrationRepository;
    public customerController(CustomerRepository customerRepository,ClientRegistrationRepository clientRegistrationRepository){
        this.customerRepository = customerRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }
    @GetMapping("/customers")
    @PostAuthorize("hasAuthority('Admin')")
    public String customers(Model  model){
        List<Customer> customerList =  customerRepository.findAll();
        model.addAttribute("customers",customerList);
        return "customers";
    }
    @GetMapping("/products")
    public String products(Model model){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
        String jwtTokenValue = oidcUser.getIdToken().getTokenValue();
        RestClient restClient  = RestClient.create("http://localhost:9091");
        List<Product> products = restClient.get()
                .uri("/products")
                .headers(httpHeaders -> httpHeaders.set(HttpHeaders.AUTHORIZATION,"Bearer "+jwtTokenValue))
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
        model.addAttribute("products",products);
        return "products";
    }
    @GetMapping("/auth")
    @ResponseBody
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }
    @GetMapping("/")
    public String home(Model model){
        return "home";
    }
    @GetMapping("/NotAuthorized")
    public String NotAuthorized(){
        return "NotAuthorized";
    }
    @GetMapping("/oauth2Login")
    public String oauth2Login(Model model){
        String authorizationRequestBaseUri = "oauth2/authorization";
        Map<String,String> oauthAuthenticationUrls = new HashMap<>();
        Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        clientRegistrations.forEach((registration)->{
            oauthAuthenticationUrls.put(registration.getClientName(),authorizationRequestBaseUri+'/'+registration.getRegistrationId());
        });
        model.addAttribute("urls",oauthAuthenticationUrls);
        return "oauth2Login";
    }
}
