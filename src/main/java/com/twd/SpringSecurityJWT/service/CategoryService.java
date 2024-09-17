package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.CategoryDTO;
import com.twd.SpringSecurityJWT.entity.Category;
import com.twd.SpringSecurityJWT.entity.Image;
import com.twd.SpringSecurityJWT.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageService imageService;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=new Category();
        category.setName(categoryDTO.getName());
        Image image=imageService.getImage(categoryDTO.getImageId());
        category.setImage(image);
        categoryRepository.save(category);
        return convertDTO(category);
    }


    public CategoryDTO convertDTO(Category category)
    {
        CategoryDTO categoryDTO=new CategoryDTO();
         categoryDTO.setName(category.getName());
         categoryDTO.setImageId(category.getImage().getId());
         return categoryDTO;
    }

    /*
    public Category updateCategory(Long id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

     */
    public String updateCategory(String name, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            categoryRepository.save(category);
            return "Category updated successfully!";
        } else {
            return "Category not found!";
        }
    }


    public boolean deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            categoryRepository.deleteById(id);
            return true; // Deletion was successful
        } else {
            return false; // Category not found, deletion failed
        }
    }
}
