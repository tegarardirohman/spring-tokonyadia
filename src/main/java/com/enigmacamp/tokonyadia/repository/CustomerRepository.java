package com.enigmacamp.tokonyadia.repository;

import com.enigmacamp.tokonyadia.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
