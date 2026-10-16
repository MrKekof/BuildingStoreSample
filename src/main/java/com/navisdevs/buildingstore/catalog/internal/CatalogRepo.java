package com.navisdevs.buildingstore.catalog.internal;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 interface CatalogRepo extends JpaRepository<CatalogEntity, Long> {
    Page <CatalogEntity> findByCategoryIgnoreCase(String category , Pageable pageable);
}
