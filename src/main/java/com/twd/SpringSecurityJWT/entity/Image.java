package com.twd.SpringSecurityJWT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;



    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    // Getters and setters



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
