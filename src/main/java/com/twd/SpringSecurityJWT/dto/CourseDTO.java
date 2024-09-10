package com.twd.SpringSecurityJWT.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    private String name;
    private String description;
    private Long categoryId;
    private Long ourUsersId;
    private Long imageId;  // Add this field to reference the image
    private String imageData;  // Image data as Base64 string
    private List<ChapterDTO> chapters;  // New field for chapters
}
