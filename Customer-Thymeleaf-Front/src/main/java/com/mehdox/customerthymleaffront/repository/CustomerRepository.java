package com.mehdox.customerthymleaffront.repository;

import com.mehdox.customerthymleaffront.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
