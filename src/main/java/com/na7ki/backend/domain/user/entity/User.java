package com.na7ki.backend.domain.user.entity;

import com.na7ki.backend.auth.verificationcode.VerificationCode;
import com.na7ki.backend.domain.user.entity.auxililary.Gender;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 68)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false, length = 6)
    private Gender gender;

    @Column (nullable = false, unique = true, length = 13)
    private String phoneNumber;

    @Column (nullable = false)
    private Byte age;



    @Column (name = "display-image_path", nullable = false, length = 100)
    private String displayImage_path;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private VerificationCode verificationCode;



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

    public String getRole() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRole()));
    }
}
