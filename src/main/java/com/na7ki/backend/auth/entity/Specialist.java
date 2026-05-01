package com.na7ki.backend.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "specialist")
@DiscriminatorValue("SPECIALIST")
@Data
public class Specialist extends User {

    @Column(name="specialist_id", nullable = false, unique=true, updatable=false, length=15)
    private String specialistID;

    @Column (name = "date_of_birth", nullable = false, updatable=false)
    private LocalDate dateOfBirth;

    @Column (nullable = false, length = 300)
    private String address;

    @ElementCollection
    @CollectionTable (name = "specialist-personal_images", joinColumns = @JoinColumn(name = "user_id"))
    @Column (name = "personal_image_path", nullable = false, length = 100)
    private List<String> personalImages_paths;

}
