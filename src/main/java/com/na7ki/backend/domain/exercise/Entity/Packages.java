package com.na7ki.backend.domain.exercise.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "packages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;



    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    @ManyToMany
    @JoinTable(
        name = "package_images",
        joinColumns = @JoinColumn(name = "package_id"),
        inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<Image> images;
}
