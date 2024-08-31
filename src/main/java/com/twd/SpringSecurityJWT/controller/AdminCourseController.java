package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.dto.CourseDTO;
import com.twd.SpringSecurityJWT.entity.*;
import com.twd.SpringSecurityJWT.repository.CourseRepository;
import com.twd.SpringSecurityJWT.repository.CategoryRepository;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import com.twd.SpringSecurityJWT.service.CategoryService;
import com.twd.SpringSecurityJWT.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.util.Base64Utils;
import java.util.Base64
;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OurUserRepo ourUsersRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/all/courses")
    public List<CourseDTO> getAllCourses() {
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

    @PostMapping("/save")
    public ResponseEntity<?> saveCourse(@RequestBody CourseDTO courseDTO) {
        // Log the incoming courseDTO to check values
        System.out.println("Received CourseDTO: " + courseDTO);

        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        Image image = imageService.getImage(courseDTO.getImageId());

        // Fetch Category and OurUsers objects using the provided IDs
        Category category = categoryRepository.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        OurUsers ourUser = ourUsersRepository.findById(Math.toIntExact(courseDTO.getOurUsersId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        course.setCategory(category);
        course.setOurUsers(ourUser);
        course.setImage(image);  // Set the image to the course
        Course savedCourse = courseRepository.save(course);

        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }







    @PutMapping("/update/course/{name}")
    public ResponseEntity<?> updateCourse(@PathVariable String name, @RequestBody CourseDTO courseDTO) {
        // Log the incoming courseDTO to check values
        System.out.println("Received CourseDTO for update: " + courseDTO);

        return courseRepository.findByName(name)
                .map(course -> {
                    // Update course fields with values from the DTO
                    course.setName(courseDTO.getName());
                    course.setDescription(courseDTO.getDescription());

                    // Update image

                    Image image = imageService.getImage(courseDTO.getImageId());
                    // Fetch and set Category and OurUsers using the provided IDs
                    Category category = categoryRepository.findById(courseDTO.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    OurUsers ourUser = ourUsersRepository.findById(Math.toIntExact(courseDTO.getOurUsersId()))
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    course.setCategory(category);
                    course.setOurUsers(ourUser);
                    course.setImage(image);

                    // Save the updated course
                    Course updatedCourse = courseRepository.save(course);
                    return ResponseEntity.ok().body(updatedCourse);
                })
                .orElse(ResponseEntity.notFound().build());
    }





    @DeleteMapping("/delete/course/{name}")
    public ResponseEntity<String> deleteCourse(@PathVariable String name) {
        Optional<Course> courseOptional = courseRepository.findByName(name);
        if (courseOptional.isPresent()) {
            courseRepository.delete(courseOptional.get());
            return ResponseEntity.ok("Le cours avec le nom '" + name + "' a été supprimé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le cours avec le nom '" + name + "' n'a pas été trouvé.");
        }
    }





  /*  @PostMapping("/{courseId}/user/{userId}")
    public ResponseEntity<?> assignUserToCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        Optional<OurUsers> userOptional = ourUsersRepository.findById(userId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent() && userOptional.isPresent()) {
            Course course = courseOptional.get();
            OurUsers user = userOptional.get();
            course.setOurUsers(user);
            Course updatedCourse = courseRepository.save(course);
            return ResponseEntity.ok().body(updatedCourse);
        }
        return ResponseEntity.notFound().build();
    }

   */

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Course>> getCoursesByUser(@PathVariable Long userId) {
        Optional<OurUsers> userOptional = ourUsersRepository.findById(Math.toIntExact(userId));

        if (userOptional.isPresent()) {
            OurUsers user = userOptional.get();
            List<Course> courses = courseRepository.findByOurUsers(user);
            return ResponseEntity.ok().body(courses);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<OurUsers>> getAllUsers() {
        List<OurUsers> users = ourUsersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }



}
