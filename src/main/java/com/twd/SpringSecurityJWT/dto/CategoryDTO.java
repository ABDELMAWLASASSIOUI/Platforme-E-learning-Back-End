package com.twd.SpringSecurityJWT.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDTO {
    private String name;
    private Long imageId;
    private String imageData;
}
