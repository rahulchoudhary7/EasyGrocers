package com.easygrocers.javabackend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String category;

    private String subCategory;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal itemPrice;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private boolean isVeg;

    @Column(nullable = false)
    private String weight;

    @ElementCollection
    private List<String> types;

}
