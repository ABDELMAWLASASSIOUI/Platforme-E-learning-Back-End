package com.twd.SpringSecurityJWT.repository;

import com.twd.SpringSecurityJWT.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}