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

    /*
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

     */
    @GetMapping
    public ResponseEntity<?> getAllCategories()
    {
        try {
            return ResponseEntity.ok(categoryService.getAllCategorie());

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the liste of categries is not found or to do probleme");
        }
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
  /*  @PutMapping("/update/{name}")
    public ResponseEntity<String> updateCategory(@PathVariable(value = "name") String name, @RequestBody Category categoryDetails) {
        String resultMessage = categoryService.updateCategory(name, categoryDetails);
        if (resultMessage.equals("Category updated successfully!")) {
            return ResponseEntity.ok(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMessage);
        }
    }

   */
    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateCategory(@PathVariable(value = "name") String name, @RequestBody CategoryDTO categoryDTO) {
        try {
            // Appeler le service pour mettre à jour la catégorie
            CategoryDTO updatedCategory = categoryService.updateCategory(name, categoryDTO);
            // Retourner une réponse 200 avec la catégorie mise à jour
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            // Si la catégorie n'est pas trouvée, retourner une réponse 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found: " + name);
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
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategorie(@PathVariable String name) {
        boolean isDeleted = categoryService.deleteCategory(name);
        if (isDeleted) {
            return ResponseEntity.ok("Category successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category in not found or not be deleted");
        }

    }
}

