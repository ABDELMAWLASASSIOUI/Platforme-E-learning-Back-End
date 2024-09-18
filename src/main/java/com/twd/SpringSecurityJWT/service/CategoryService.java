package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.dto.CategoryDTO;
import com.twd.SpringSecurityJWT.entity.Category;
import com.twd.SpringSecurityJWT.entity.Image;
import com.twd.SpringSecurityJWT.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageService imageService;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    /*
        @GetMapping("/user/all/courses")
    public List<CourseDTO> getAllCoursesOfUser() {
        return courseRepository.findAll().stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName(course.getName());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setCategoryId(course.getCategory().getId());
            courseDTO.setOurUsersId(Long.valueOf(course.getOurUsers().getId()));

            // Convert image data to Base64 string
            Image image = course.getImage();
            if (image != null) {
                //String base64Image = Base64Utils.encodeToString(image.getData());
                String encodedString = Base64.getEncoder().encodeToString(image.getData());
                courseDTO.setImageData("data:" + image.getType() + ";base64," + encodedString);
                courseDTO.setImageId(image.getId());  // Set the image ID
            }

            return courseDTO;
        }).collect(Collectors.toList());
    }
     */
      public List<CategoryDTO> getAllCategorie()
      {
           return categoryRepository.findAll().stream().map(category -> {

               CategoryDTO categoryDTO=new CategoryDTO();
                 categoryDTO.setName(category.getName());
                       Image image = category.getImage();
                       if (image != null) {
                           String encodedString = Base64.getEncoder().encodeToString(image.getData());
                           categoryDTO.setImageData("data:" + image.getType() + ";base64," + encodedString);
                           categoryDTO.setImageId(image.getId());  // Set the image ID
                       }
                       return categoryDTO;
                   }).collect(Collectors.toList());
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

    public CategoryDTO updateCategory(String name,CategoryDTO categoryDTO)
    {
        Category existeOrNotIdCategory=categoryRepository.findByName(name).orElseThrow(()-> new RuntimeException("this is name is not found"));
          existeOrNotIdCategory.setName(categoryDTO.getName());
          Image image=imageService.getImage(categoryDTO.getImageId());
          existeOrNotIdCategory.setImage(image);
        Category updateCategory=categoryRepository.save(existeOrNotIdCategory);

          return convertDTO(updateCategory);//add
    }


    public boolean deleteCategory(String name) {
        Optional<Category> categoryOptional = categoryRepository.findByName(name);

        if (categoryOptional.isPresent()) {
            categoryRepository.delete(categoryOptional.get());
            return true; // Deletion was successful
        } else {
            return false; // Category not found, deletion failed
        }
    }



}
