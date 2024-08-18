package com.easygrocers.javabackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easygrocers.javabackend.entity.Product;

public interface ProductRepo extends JpaRepository<Product, UUID> {

}
