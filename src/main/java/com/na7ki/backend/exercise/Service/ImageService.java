package com.na7ki.backend.exercise.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.na7ki.backend.exercise.Entity.Image;
import com.na7ki.backend.exercise.Repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public ImageService(Cloudinary cloudinary, ImageRepository imageRepository) {
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(MultipartFile file, String folder) throws Exception {

        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", folder)
        );

        String url = (String) uploadResult.get("secure_url");
        String publicId = (String) uploadResult.get("public_id");

        Image image = new Image();
        image.setImageUrl(url);
        image.setImageName(file.getOriginalFilename());
        image.setFolderName(folder);
        image.setPublicId(publicId);

        return imageRepository.save(image);
    }

    public Map<String, List<Image>> getImagesGroupedByFolder() {

        List<Image> images = imageRepository.findAll();

        return images.stream()
                .collect(Collectors.groupingBy(
                        Image::getFolderName
                ));
    }
}
