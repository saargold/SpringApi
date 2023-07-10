package com.devops.webapp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> getProductsByCategory(String name);

    List<Product> getProductsByTitle(String name);

}