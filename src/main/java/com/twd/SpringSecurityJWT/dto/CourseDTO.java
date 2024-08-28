package com.twd.SpringSecurityJWT.dto;


import lombok.Data;

@Data
public class CourseDTO {
    private String name;
    private String description;
    private Long categoryId;  // or Integer/Long depending on your ID type
    private Long ourUsersId;

    // Getters and Setters
}