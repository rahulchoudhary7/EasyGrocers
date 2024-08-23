package com.easygrocers.javabackend.controller;

import com.easygrocers.javabackend.dto.CategoryDTO;
import com.easygrocers.javabackend.entity.UserDetails;
import com.easygrocers.javabackend.exception.NotAuthorizedException;
import com.easygrocers.javabackend.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        if(!userDetails.getUserType().equals("admin")){
            throw new NotAuthorizedException("Unauthorized");
        }
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/updateCategory")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestParam UUID categoryId,
            @RequestBody CategoryDTO categoryDTO, HttpServletRequest request)  {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        if(!userDetails.getUserType().equals("admin")){
            throw new NotAuthorizedException("Unauthorized");
        }
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok(updatedCategoryDTO);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestParam UUID categoryId, HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        if(!userDetails.getUserType().equals("admin")){
            throw new NotAuthorizedException("Unauthorized");
        }
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category deleted successfully");

    }
}
