package com.easygrocers.javabackend.service;

import com.easygrocers.javabackend.dto.CategoryDTO;
import com.easygrocers.javabackend.entity.Category;
import com.easygrocers.javabackend.exception.ResourceNotFoundException;
import com.easygrocers.javabackend.repository.CategoryRepo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryDTO updateCategory(UUID categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("No category found with id: " + categoryId));

        BeanUtils.copyProperties(categoryDTO, category, "id");

        Category updatedCategory = categoryRepo.save(category);

        return convertToDTO(updatedCategory);

    }

    public void deleteCategory(UUID categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new ResourceNotFoundException("No category found with id: " + categoryId);
        }

        categoryRepo.deleteById(categoryId);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        return category;
    }
}
