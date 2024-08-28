package com.twd.SpringSecurityJWT.controller;


import com.twd.SpringSecurityJWT.dto.CourseDTO;
import com.twd.SpringSecurityJWT.entity.Category;
import com.twd.SpringSecurityJWT.entity.Course;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import com.twd.SpringSecurityJWT.repository.CategoryRepository;
import com.twd.SpringSecurityJWT.repository.CourseRepository;
import com.twd.SpringSecurityJWT.repository.OurUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OurUserRepo ourUsersRepository;


    @GetMapping ("/admin/all/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/admin/get/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
    }

    //    @PostMapping("/admin/save")
//    public Course createCourse(@RequestBody Course course) {
//        System.out.println("Received Course: " + course); // Debugging line
//        System.out.println("Course Name: " + course.getName());
//        System.out.println("Course Description: " + course.getDescription());
//        System.out.println("Category ID: " + (course.getCategory() != null ? course.getCategory().getId() : null));
//        System.out.println("User ID: " + (course.getOurUser() != null ? course.getOurUser().getId() : null));
//
//        return courseRepository.save(course);
//    }
    @PostMapping("/admin/save")
    public ResponseEntity<Course> saveCourse(@RequestBody CourseDTO courseDTO) {
        // Log the incoming courseDTO to check values
        System.out.println("Received CourseDTO: " + courseDTO);

        // Assuming CourseDTO contains categoryId and ourUsersId
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());

        // Fetch Category and OurUsers objects using the provided IDs
        Category category = categoryRepository.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        OurUsers ourUser = ourUsersRepository.findById(Math.toIntExact(courseDTO.getOurUsersId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        course.setCategory(category);
        course.setOurUser(ourUser);

        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.ok(savedCourse);
    }

    @PutMapping("/admin/update/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        // Log the incoming courseDTO to check values
        System.out.println("Received CourseDTO: " + courseDTO);

        return courseRepository.findById(id)
                .map(course -> {
                    // Update course fields with values from the DTO
                    course.setName(courseDTO.getName());
                    course.setDescription(courseDTO.getDescription());

                    // Fetch and set Category and OurUsers using the provided IDs
                    Category category = categoryRepository.findById(courseDTO.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    OurUsers ourUser = ourUsersRepository.findById(Math.toIntExact(courseDTO.getOurUsersId()))
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    course.setCategory(category);
                    course.setOurUser(ourUser);

                    // Save the updated course
                    Course updatedCourse = courseRepository.save(course);
                    return ResponseEntity.ok().body(updatedCourse);
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/admin/delete/course/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.delete(course);
                    return ResponseEntity.ok("Le cours avec l'ID " + id + " a été supprimé avec succès.");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le cours avec l'ID " + id + " n'a pas été trouvé."));
    }






    @PostMapping("/{courseId}/user/{userId}") //not testiong is corect or not
    public ResponseEntity<Course> assignUserToCourse(@PathVariable Long courseId, @PathVariable Integer userId) {
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

    @GetMapping("/user/{userId}")//not testiong is corect or not
    public ResponseEntity<List<Course>> getCoursesByUser(@PathVariable Integer userId) {
        Optional<OurUsers> userOptional = ourUsersRepository.findById(userId);

        if (userOptional.isPresent()) {
            OurUsers user = userOptional.get();
            List<Course> courses = courseRepository.findByOurUsers(user);
            return ResponseEntity.ok().body(courses);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/users")//not testiong is corect or not
    public ResponseEntity<List<OurUsers>> getAllUsers() {
        List<OurUsers> users = ourUsersRepository.findAll();

        return ResponseEntity.ok(users);

}


}