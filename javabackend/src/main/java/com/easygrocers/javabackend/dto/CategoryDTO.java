package com.easygrocers.javabackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;
    @NotBlank(message = "Category name is required")
    private String name;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}
