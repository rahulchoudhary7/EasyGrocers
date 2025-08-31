package com.easygrocers.categories.services;


import com.easygrocers.categories.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO, String userRole, String userId);

    CategoryDTO updateCategory(UUID categoryId, CategoryDTO categoryDTO, String userRole, String userId);

    void deleteCategory(UUID categoryId, String userRole, String userId);
}
