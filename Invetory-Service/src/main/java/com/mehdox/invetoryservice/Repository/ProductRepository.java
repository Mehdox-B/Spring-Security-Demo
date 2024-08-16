package com.mehdox.invetoryservice.Repository;

import com.mehdox.invetoryservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
