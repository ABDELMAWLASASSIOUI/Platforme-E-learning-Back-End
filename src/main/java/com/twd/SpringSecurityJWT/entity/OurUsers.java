package com.twd.SpringSecurityJWT.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "ourusers")
public class OurUsers implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String image;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Birth date must be in the format yyyy-MM-dd")
    private Date birth_date;
    @OneToMany(mappedBy = "user")
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> chats;
    @Pattern(regexp = "ADMIN|USER", message = "Role must be ADMIN or USER")
    private String role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
