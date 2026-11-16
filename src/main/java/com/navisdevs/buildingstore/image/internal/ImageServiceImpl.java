package com.navisdevs.buildingstore.image.internal;

import com.navisdevs.buildingstore.image.ImageResponse;
import com.navisdevs.buildingstore.image.ImageService;
import com.navisdevs.buildingstore.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;

    @Override
    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(fileName);
        imageEntity.setContentType(file.getContentType());
        imageEntity.setData(file.getBytes());
        
        ImageEntity savedImage = imageRepo.save(imageEntity);
        
        // Return the API URL to access this image
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
