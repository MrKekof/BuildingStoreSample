package com.navisdevs.buildingstore.content.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface PageRepo extends JpaRepository<PageEntity, Long> {

    Optional<PageEntity> findBySlug(String slug);

    Page<PageEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<PageEntity> findBySlugContainingIgnoreCaseOrTitleContainingIgnoreCase(String slug, String title, Pageable pageable);
}

