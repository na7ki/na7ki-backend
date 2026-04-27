package com.na7ki.backend.auth.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "specialist")
@DiscriminatorValue("SPECIALIST")
public class Specialist extends User {

    @Column (name = "educational_degree_details", nullable = false, length = 150)
    private String educationalDegreeDetails;

    @ElementCollection
    @CollectionTable (name = "specialist-personal_images", joinColumns = @JoinColumn(name = "id"))
    @Column (name = "personal_image_path", nullable = false, unique = true, length = 100)
    private List<String> personalImages_paths;

}
