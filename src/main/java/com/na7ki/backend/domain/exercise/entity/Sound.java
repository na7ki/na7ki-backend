package com.na7ki.backend.domain.exercise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sounds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String soundUrl;

    @Column(nullable = false)
    private String soundName;

    @Column(nullable = false)
    private String folderName;

    @Column(unique = true)
    private String publicId;

    private Long size;

    private String format;
}
