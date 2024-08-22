package com.easygrocers.javabackend.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.easygrocers.javabackend.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = " Product is required")
    private Product product;

    @NotNull(message = "Quantity is required")
    private Integer quantity;
    
    @NotNull(message = "Price is required")
    private BigDecimal price;
}
