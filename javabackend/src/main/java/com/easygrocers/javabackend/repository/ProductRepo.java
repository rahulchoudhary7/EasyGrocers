package com.easygrocers.javabackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easygrocers.javabackend.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {

}
