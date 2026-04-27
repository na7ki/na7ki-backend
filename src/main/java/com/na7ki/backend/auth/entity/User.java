package com.na7ki.backend.auth.entity;

import com.na7ki.backend.auth.auxiliary.Address;
import com.na7ki.backend.auth.auxiliary.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Data
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 68)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false, length = 6)
    private Gender gender;

    @Column (name = "date_of_birth")
    private Date dateOfBirth;

    @Column (nullable = false)
    private int age;

    @ElementCollection
    @CollectionTable (name = "user-phone_numbers", joinColumns = @JoinColumn(name = "id"))
    @Column (name = "phone_number", nullable = false, unique = true, length = 13)
    private List<String> phoneNumbers = new ArrayList<>();

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Embedded
    private Address address;

    @Column (name = "display_image_path", nullable = false, length = 100)
    private String displayImage_path;

    @Column(name = "email_verification_time")
    private Date emailVerificationTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAtDate;

    @UpdateTimestamp
    @Column(name = "last_modified_at", nullable = false)
    private Date updatedAtDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = this.getClass().getAnnotation(DiscriminatorValue.class).value();
        return List.of(new SimpleGrantedAuthority(role));
    }
}
