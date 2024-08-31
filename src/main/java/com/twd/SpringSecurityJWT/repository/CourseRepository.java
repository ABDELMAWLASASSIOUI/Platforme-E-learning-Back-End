package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Course;
import com.twd.SpringSecurityJWT.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByOurUsers(OurUsers ourUsers);
    Optional<Course> findByName(String name );
}
