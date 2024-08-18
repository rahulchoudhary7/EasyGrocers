package com.easygrocers.javabackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private UUID id;

    @NotBlank(message = "Item name is required")
    private String itemName;

    @NotBlank(message = "Category is required")
    private String category;

    private String subCategory;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Item price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Item price must be greater than 0")
    private BigDecimal itemPrice;

    @NotNull(message = "Discount is required")
    @DecimalMin(value = "0.0", message = "Discount must be 0 or greater")
    private BigDecimal discount;

    @NotBlank(message = "Image URL is required")
    private String image;

    @NotNull(message = "Vegetarian status is required")
    private Boolean isVeg;

    @NotBlank(message = "Weight is required")
    private String weight;

    private List<String> types;
}
