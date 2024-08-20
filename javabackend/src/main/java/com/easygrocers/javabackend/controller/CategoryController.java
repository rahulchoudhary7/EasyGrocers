package com.easygrocers.javabackend.controller;

import com.easygrocers.javabackend.dto.CategoryDTO;
import com.easygrocers.javabackend.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setImage(imageFile.getBytes());

        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/udpateCategory")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestParam UUID categoryId,
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setImage(imageFile.getBytes());
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestParam UUID categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category deleted successfully");

    }
}
