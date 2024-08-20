package com.easygrocers.javabackend.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.UUID;

import com.easygrocers.javabackend.entity.Product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InventoryItemDTO {
    private UUID id;

    @NotBlank(message = "Seller ID is required")
    private String sellerId;
    @NotBlank(message = " Product is required")
    private Product product;

    @NotBlank(message = "Quantity is required")
    private Integer quantity;
    
    @NotBlank(message = "Price is required")
    private BigDecimal price;
}
