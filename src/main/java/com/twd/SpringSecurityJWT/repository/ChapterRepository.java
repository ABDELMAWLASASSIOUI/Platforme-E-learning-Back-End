package com.twd.SpringSecurityJWT.repository;


import com.twd.SpringSecurityJWT.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    List<Chapter> findByCourseId(Long  courseId);
}