package com.mehdox.invetoryservice;

import com.mehdox.invetoryservice.entities.Product;
import com.mehdox.invetoryservice.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class InvetoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvetoryServiceApplication.class, args);
    }
   @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return (args -> {
           productRepository.save(Product.builder().id(UUID.randomUUID().toString()).name("Lenovo E15").price(1000.0).quantity(1).build());
           productRepository.save(Product.builder().id(UUID.randomUUID().toString()).name("Iphone 15 Pro").price(1200.0).quantity(1).build());
           productRepository.save(Product.builder().id(UUID.randomUUID().toString()).name("Hp Scanner").price(500.0).quantity(3).build());
        });
   }
}
