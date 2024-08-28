package com.twd.SpringSecurityJWT.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Constructeurs, getters et setters


    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers ourUsers;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Course() {
    }

    public Course(String name, String description,OurUsers ourUsers,Category category) {
        this.name = name;
        this.description = description;
        this.ourUsers=ourUsers;
        this.category=category;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public OurUsers getOurUsers() {
        return ourUsers;
    }

    public void setOurUsers(OurUsers ourUsers) {
        this.ourUsers = ourUsers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public OurUsers getOurUser() {  // Ensure this method exists
        return ourUsers;
    }

    public void setOurUser(OurUsers ourUser) {
        this.ourUsers = ourUser;
    }
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + (category != null ? category.getId() : null) +
                ", ourUsersId=" + (ourUsers != null ? ourUsers.getId() : null) +
            '}';
}
}