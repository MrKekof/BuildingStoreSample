package com.navisdevs.buildingstore.catalog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {
Page<CatalogResponse> getCatalog(String category, Pageable pageable);
CatalogResponse createProduct(CatalogRequest catalogRequest);
CatalogResponse updateProduct(Long id , CatalogRequest catalogRequest);
void  deleteProduct(Long id);
}
