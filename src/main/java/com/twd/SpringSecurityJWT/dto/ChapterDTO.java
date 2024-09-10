package com.twd.SpringSecurityJWT.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ChapterDTO {
    private Long id; // Add this field to hold the chapter's ID
    private String title;
    private String content;
    private Long courseId; // L'ID du cours associé

    // Getters et setters
}