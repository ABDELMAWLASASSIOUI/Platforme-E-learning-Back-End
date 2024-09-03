package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    void deleteByName(String name);
}