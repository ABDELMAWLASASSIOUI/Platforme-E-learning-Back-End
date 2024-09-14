package com.twd.SpringSecurityJWT.repository;


import com.twd.SpringSecurityJWT.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    List<Chapter> findByCourseId(Long  courseId);

    Optional<Chapter> findByTitle(String title);
}