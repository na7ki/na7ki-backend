// package com.na7ki.backend.exercise;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Lob;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// import java.util.List;

// @Entity
// @Table(name = "images")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class Image {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String label;

//     private String imageUrl;

//     private String type;


// }






//====================new 



package com.na7ki.backend.exercise;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cloudinary URL (required)
    @Column(nullable = false, length = 1000)
    private String imageUrl;

    // Original file name from local machine
    @Column(nullable = false)
    private String imageName;

    // Folder grouping (singular / plural / etc.)
    @Column(nullable = false)
    private String folderName;

    // Cloudinary public ID (IMPORTANT for delete/update later)
    @Column(unique = true)
    private String publicId;

    // Optional metadata (useful for future features)
    private Long size;

    private String format;

    // =========================
    // Constructors
    // =========================

    public Image() {}

    public Image(String imageUrl, String imageName, String folderName) {
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.folderName = folderName;
    }

    // =========================
    // Getters & Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
