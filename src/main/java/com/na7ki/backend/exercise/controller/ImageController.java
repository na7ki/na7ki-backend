package com.na7ki.backend.exercise.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.na7ki.backend.exercise.Entity.Image;
import com.na7ki.backend.exercise.Repository.ImageRepository;
import com.na7ki.backend.exercise.Service.CloudinaryFolderUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageRepository imageRepository;
    private final Cloudinary cloudinary;
    private final CloudinaryFolderUploadService folderService;

    public ImageController(
            ImageRepository imageRepository,
            Cloudinary cloudinary,
            CloudinaryFolderUploadService folderService
    ) {
        this.imageRepository = imageRepository;
        this.cloudinary = cloudinary;
        this.folderService = folderService;
    }

    // =========================
    // 1. Upload single image
    // =========================
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "single_uploads"
                    )
            );

            String imageUrl = (String) uploadResult.get("secure_url");

            Image image = new Image();

            image.setImageUrl(imageUrl);

            image.setImageName(file.getOriginalFilename());

            image.setFolderName("single_uploads");

            imageRepository.save(image);

            return ResponseEntity.ok(
                    Map.of(
                            "message", "Image uploaded successfully",
                            "imageUrl", imageUrl
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    "Upload failed: " + e.getMessage()
            );
        }
    }

    // =========================
    // 2. Get image by ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(
            @PathVariable Long id
    ) {

        Image image = imageRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Image not found"));

        return ResponseEntity.ok(
                Map.of(
                        "id", image.getId(),
                        "imageName", image.getImageName(),
                        "folderName", image.getFolderName(),
                        "imageUrl", image.getImageUrl()
                )
        );
    }

    // =========================
    // 3. Upload folders
    // =========================
    @PostMapping("/upload-folder")
    public ResponseEntity<?> uploadFolders() {

        try {

            folderService.uploadFolder(
                    "/home/maiawad/FCAI/na7ki-backend/cognition_packages/cover",
                    "cover"
            );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/trace_path",
                     "trace_path"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/color_sort",
                     "color_sort"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/copy_animal",
                     "copy_animal"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/emotions",
                     "emotions"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/feed_bear",
                     "feed_bear"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/match_alike",
                     "match_alike"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/put_object",
                     "put_object"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/sound_match",
                     "sound_match"
             );

             folderService.uploadFolder(
                     "/home/maiawad/FCAI/na7ki-backend/cognition_packages/where_is",
                     "where_is"
             );









            return ResponseEntity.ok(
                    "Folders uploaded successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    "Folder upload failed: " + e.getMessage()
            );
        }
    }

    // =========================
    // 4. Get all images
    // =========================
    @GetMapping
    public ResponseEntity<?> getAllImages() {

        return ResponseEntity.ok(
                imageRepository.findAll()
        );
    }

    // =========================
    // 5. Get images grouped by folder
    // =========================
    @GetMapping("/folders")
    public ResponseEntity<?> getImagesByFolder() {

        List<Image> images = imageRepository.findAll();

        Map<String, List<Map<String, Object>>> result =
                images.stream()
                        .collect(Collectors.groupingBy(

                                Image::getFolderName,

                                Collectors.mapping(

                                        image -> Map.of(
                                                "id", image.getId(),
                                                "imageName", image.getImageName(),
                                                "imageUrl", image.getImageUrl()
                                        ),

                                        Collectors.toList()
                                )
                        ));

        return ResponseEntity.ok(result);
    }
}
