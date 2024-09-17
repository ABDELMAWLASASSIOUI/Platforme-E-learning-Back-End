package com.twd.SpringSecurityJWT.controller;


import com.twd.SpringSecurityJWT.dto.CategoryDTO;
import com.twd.SpringSecurityJWT.entity.Category;
import com.twd.SpringSecurityJWT.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /*
    @GetMapping("/get/{id}")//not worked
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

     */

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add") //is worked
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO);
    }


    //this methode to do update of category by id
    @PutMapping("/update/{name}")
    public ResponseEntity<String> updateCategory(@PathVariable(value = "name") String name, @RequestBody Category categoryDetails) {
        String resultMessage = categoryService.updateCategory(name, categoryDetails);
        if (resultMessage.equals("Category updated successfully!")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMessage);
        }
    }

/*
    @DeleteMapping("/{id}")//is worked
    public ResponseEntity<String> deleteCategory(@PathVariable L name) {
        boolean isDeleted = categoryService.deleteCategory(name); // Assuming deleteCategory returns a boolean

        if (isDeleted) {
            return ResponseEntity.ok("Category successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found or could not be deleted.");
        }
    }

 */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);
        if (isDeleted) {
            return ResponseEntity.ok("Category successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category in not found or not be deleted");
        }

    }
}

