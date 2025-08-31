package com.easygrocers.categories.controllers;

import com.easygrocers.categories.dto.CategoryDTO;
import com.easygrocers.categories.services.CategoryServiceImpl;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, @RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId) {

        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO, userRole, userId);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable UUID categoryId,
                                                      @Valid @RequestBody CategoryDTO categoryDTO, @RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId)  {


        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO, userRole, userId);

        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID categoryId, @RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId) {
        categoryService.deleteCategory(categoryId, userRole, userId);

        return ResponseEntity.ok("Category deleted successfully");

    }
}
