package com.navisdevs.buildingstore.news.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 interface NewsRepo extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findAllByPublished(boolean published, Pageable pageable);
    
    Page<NewsEntity> findAllByTitleContainingIgnoreCaseAndPublished(String title, boolean published, Pageable pageable);
    
    Page<NewsEntity> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
}
