package com.navisdevs.buildingstore.content.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepo extends JpaRepository<PageEntity, Long> {


    Optional<PageEntity> findBySlug(String slug);

    Page<PageEntity> findByTitleContainingIgnoreCase(String slug, Pageable pageable);
}
