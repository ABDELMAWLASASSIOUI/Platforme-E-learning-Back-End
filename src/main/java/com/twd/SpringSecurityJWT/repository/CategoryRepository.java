package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}