package com.navisdevs.buildingstore.image.internal;

import com.navisdevs.buildingstore.image.ImageResponse;
import com.navisdevs.buildingstore.image.ImageService;
import com.navisdevs.buildingstore.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;

    @Override
    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Файл изображения не может быть пустым");
        }

        String originalFilename = Objects.requireNonNullElse(file.getOriginalFilename(), "image");
        String fileName = StringUtils.cleanPath(originalFilename);
        String contentType = Objects.requireNonNullElse(file.getContentType(), "application/octet-stream");

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(fileName);
        imageEntity.setContentType(contentType);
        imageEntity.setData(file.getBytes());

        ImageEntity savedImage = imageRepo.save(imageEntity);

        return "/api/images/" + savedImage.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public ImageResponse getImage(Long id) {
        ImageEntity imageEntity = imageRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Изображение с id " + id + " не найдено"));

        return new ImageResponse(
                imageEntity.getId(),
                imageEntity.getName(),
                imageEntity.getContentType(),
                imageEntity.getData()
        );
    }
}

