package com.na7ki.backend.domain.user.entity;

import com.na7ki.backend.account_management.customer_inquiry.entity.BugReport;
import com.na7ki.backend.account_management.customer_inquiry.entity.ContactRequest;
import com.na7ki.backend.domain.user.entity.enums.Gender;
import com.na7ki.backend.domain.user.verification_code.VerificationCode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
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



    @Column (name = "display-image_path", length = 60000)
    private String displayImage_path;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private VerificationCode verificationCode;

    @OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactRequest> contactRequests = new ArrayList<>();

    @OneToMany(mappedBy = "inquirer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BugReport> bugReports = new ArrayList<>();



    @Column(name = "email_verification_time")
    private Date emailVerificationTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAtDate;

    @UpdateTimestamp
    @Column(name = "last_modified_at", nullable = false)
    private Date updatedAtDate;

    @Column(nullable = false)
    private Boolean isDeleted = false;





    public void anonymize(Long deletionUserId) {
        this.setIsDeleted(true);

        //delete all personally identifying data
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        this.setName("deleted user");
        this.setEmail("deleted_" + timestamp + "@na7ki.com");
        this.setPhoneNumber("deleted_" + deletionUserId);
        this.setDisplayImage_path(null);
    }





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
