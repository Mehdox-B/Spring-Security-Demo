package com.mehdox.customerthymleaffront;

import com.mehdox.customerthymleaffront.entities.Customer;
import com.mehdox.customerthymleaffront.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerThymleafFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerThymleafFrontApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.save(Customer.builder().name("Mehdox").email("mehdox@gmail.com").build());
            customerRepository.save(Customer.builder().name("Mehdox1").email("mehdox1@gmail.com").build());
            customerRepository.save(Customer.builder().name("Mehdox2").email("mehdox2@gmail.com").build());
        };
    }

}
