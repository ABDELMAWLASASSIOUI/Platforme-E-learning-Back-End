package com.twd.SpringSecurityJWT.controller;


import com.twd.SpringSecurityJWT.entity.Course;
import com.twd.SpringSecurityJWT.entity.OurUsers;
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
    private OurUserRepo ourUsersRepository;

    @GetMapping ("/user/all/courses")
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/admin/get/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id)
                .map(course -> ResponseEntity.ok().body(course))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/save")
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/admin/update/course/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(courseDetails.getName());
                    course.setDescription(courseDetails.getDescription());
                    course.setOurUsers(courseDetails.getOurUsers());
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


}

