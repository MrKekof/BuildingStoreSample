package com.navisdevs.buildingstore.catalog.internal;

import com.navisdevs.buildingstore.catalog.CatalogRequest;
import com.navisdevs.buildingstore.catalog.CatalogResponse;
import com.navisdevs.buildingstore.catalog.CatalogService;
import com.navisdevs.buildingstore.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class CatalogServiceImpl implements CatalogService {
    private final CatalogRepo catalogRepo;


    @Override
    public Page<CatalogResponse> getCatalog(String category, Pageable pageable) {
        Page<CatalogEntity> entities;
        if (category == null || category.isBlank() || category.trim().equalsIgnoreCase("все")) {
            entities = catalogRepo.findAll(pageable);
        }else  {
            String cleanedCategory = category.trim();
            entities = catalogRepo.findByCategoryIgnoreCase(cleanedCategory, pageable);
        }
        return entities.map(this::mapToResponse);
    }

    @Override
    @Transactional
    public CatalogResponse createProduct(CatalogRequest catalogRequest) {
        CatalogEntity product = new CatalogEntity();
        product.setName(catalogRequest.name());
        product.setDescription(catalogRequest.description());
        product.setCatalogImageUrl(catalogRequest.catalogImageUrl());
        product.setPrice(catalogRequest.price());
        product.setCategory(catalogRequest.category());
        product.setStandard(catalogRequest.standard());
        product.setDiameter(catalogRequest.diameter());
        product.setMaterial(catalogRequest.material());
        CatalogEntity saved = catalogRepo.save(product);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public CatalogResponse updateProduct(Long id, CatalogRequest catalogRequest) {
        CatalogEntity product = catalogRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Товар с id "+id+" не найден"));
    product.setName(catalogRequest.name());
    product.setDescription(catalogRequest.description());
    product.setCatalogImageUrl(catalogRequest.catalogImageUrl());
    product.setPrice(catalogRequest.price());
    product.setCategory(catalogRequest.category());
    product.setStandard(catalogRequest.standard());
    product.setDiameter(catalogRequest.diameter());
    product.setMaterial(catalogRequest.material());

    return mapToResponse(catalogRepo.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!catalogRepo.existsById(id)) {
            throw new ResourceNotFoundException("Невозможно удалить товар с id "+id+" не найден");
        }
        catalogRepo.deleteById(id);
    }

    private CatalogResponse mapToResponse(CatalogEntity catalogEntity) {
        return new CatalogResponse(
                catalogEntity.getId(),
                catalogEntity.getName(),
                catalogEntity.getDescription(),
                catalogEntity.getCatalogImageUrl(),
                catalogEntity.getPrice(),
                catalogEntity.getStandard(),
                catalogEntity.getMaterial(),
                catalogEntity.getDiameter(),
                catalogEntity.getCategory()
        );
    }
}
