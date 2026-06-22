package com.na7ki.backend.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("SPECIALIST")
@Data
public class Specialist extends User {

    @Column(name="specialist_id", nullable = false, unique=true, updatable=false, length=15)
    private String specialistID;

    @Column (name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column (nullable = false, length = 300)
    private String address;

    @ElementCollection
    @CollectionTable (name = "specialist-personal_image", joinColumns = @JoinColumn(name = "user_id"))
    @Column (name = "personal_image_path", length = 100)
    private List<String> personalImages_paths;

}
