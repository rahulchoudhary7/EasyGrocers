package com.easygrocers.products.repositories;


import java.util.UUID;

import com.easygrocers.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

}

