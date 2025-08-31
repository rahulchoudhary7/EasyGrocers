package com.easygrocers.categories.services;



import com.easygrocers.categories.dto.CategoryDTO;
import com.easygrocers.categories.entities.Category;
import com.easygrocers.categories.exceptions.NotAuthorizedException;
import com.easygrocers.categories.exceptions.ResourceNotFoundException;
import com.easygrocers.categories.repositories.CategoryRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
//    private RedisService redisService;

    public List<CategoryDTO> getAllCategories() {

//        List<Category> categories = redisService.getList("getAllCategories", Category.class);

//        if (categories != null) {
//            return categories.stream()
//                    .map(this::convertToDTO)
//                    .collect(Collectors.toList());
//        } else {
            List<Category> categories2 = categoryRepo.findAll();
//            redisService.setList("getAllCategories", categories2, 5L);
            return categories2.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
//        }
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO, String userRole, String userId) {

        if(!userRole.equals("ADMIN")){
            throw new NotAuthorizedException("Unauthorized");
        }
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
//        redisService.setList("getAllCategories", null, 0L);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(UUID categoryId, CategoryDTO categoryDTO, String userRole, String userId) {

        if(!userRole.equals("ADMIN")){
            throw new NotAuthorizedException("Unauthorized");
        }
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("No category found with id: " + categoryId));

        BeanUtils.copyProperties(categoryDTO, category, "id");

        Category updatedCategory = categoryRepo.save(category);
//        redisService.setList("getAllCategories", null, 0L);

        return convertToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId, String userRole, String userId) {

        if(!userRole.equals("ADMIN")){
            throw new NotAuthorizedException("Unauthorized");
        }
        if (!categoryRepo.existsById(categoryId)) {
            throw new ResourceNotFoundException("No category found with id: " + categoryId);
        }

        categoryRepo.deleteById(categoryId);
//        redisService.setList("getAllCategories", null, 0L);
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
