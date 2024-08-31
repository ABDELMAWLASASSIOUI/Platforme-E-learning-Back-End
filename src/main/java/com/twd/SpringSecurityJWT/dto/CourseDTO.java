package com.twd.SpringSecurityJWT.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private String name;
    private String description;
    private Long categoryId;
    private Long ourUsersId;
    private Long imageId;  // Add this field to reference the image
    private String imageData;  // Image data as Base64 string
}
