package com.na7ki.backend.domain.exercise.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.na7ki.backend.domain.exercise.Entity.Image;
import com.na7ki.backend.domain.exercise.Entity.Sound;
import com.na7ki.backend.domain.exercise.Repository.ImageRepository;
import com.na7ki.backend.domain.exercise.Repository.SoundRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Locale;
import java.util.Map;

@Service
public class CloudinaryFolderUploadService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;
    private final SoundRepository soundRepository;

    public CloudinaryFolderUploadService(
            Cloudinary cloudinary,
            ImageRepository imageRepository,
            SoundRepository soundRepository
    ) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
        this.soundRepository = soundRepository;
    }

    public void uploadFolder(String folderPath, String folderName) {

        File folder = new File(folderPath);

        File[] files = folder.listFiles();

        if (files == null) {
            throw new RuntimeException("Folder is empty or not found");
        }

        for (File file : files) {

            if (!file.isFile()) {
                continue;
            }

            try {

                Map<String, Object> uploadOptions = ObjectUtils.asMap(
                        "folder", folderName
                );

                // Cloudinary handles audio (including mp3) under the "video" resource type.
                if (isMp3(file.getName())) {
                    uploadOptions.put("resource_type", "video");
                }

                // Upload to Cloudinary folder
                Map<?, ?> uploadResult = cloudinary.uploader().upload(
                        file,
                        uploadOptions
                );

                if (isMp3(file.getName())) {
                    saveSound(uploadResult, file, folderName);
                } else {
                    saveImage(uploadResult, file, folderName);
                }

            } catch (Exception e) {

                System.out.println(
                        "Failed to upload: " + file.getName() + " - " + e.getMessage()
                );
            }
        }
    }

    private boolean isMp3(String fileName) {
        return fileName.toLowerCase(Locale.ROOT).endsWith(".mp3");
    }

    private void saveImage(Map<?, ?> uploadResult, File file, String folderName) {
        Image image = new Image();
        image.setImageUrl((String) uploadResult.get("secure_url"));
        image.setImageName(file.getName());
        image.setFolderName(folderName);
        image.setPublicId((String) uploadResult.get("public_id"));
        image.setFormat((String) uploadResult.get("format"));
        image.setSize(toLong(uploadResult.get("bytes")));
        imageRepository.save(image);
    }

    private void saveSound(Map<?, ?> uploadResult, File file, String folderName) {
        Sound sound = new Sound();
        sound.setSoundUrl((String) uploadResult.get("secure_url"));
        sound.setSoundName(file.getName());
        sound.setFolderName(folderName);
        sound.setPublicId((String) uploadResult.get("public_id"));
        sound.setFormat((String) uploadResult.get("format"));
        sound.setSize(toLong(uploadResult.get("bytes")));
        soundRepository.save(sound);
    }

    private Long toLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        return null;
    }
}
