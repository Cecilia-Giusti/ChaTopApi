package com.ChaTopApiSpring2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

/**
 * Represents a user of the application.
 * This entity is associated with the "users" table in the database.
 * It contains user-specific details like name, email, password, and timestamps.
 * Additionally, it implements the UserDetails interface for Spring Security authentication and authorization.
 */
@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
