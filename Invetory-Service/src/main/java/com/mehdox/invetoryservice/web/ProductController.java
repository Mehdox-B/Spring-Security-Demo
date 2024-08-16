package com.mehdox.invetoryservice.web;

import com.mehdox.invetoryservice.Repository.ProductRepository;
import com.mehdox.invetoryservice.entities.Product;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private ProductRepository productRepository;
    public ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @GetMapping("/products")
    @PostAuthorize("hasAuthority('Admin')")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
