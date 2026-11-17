package com.navisdevs.buildingstore.image.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ImageRepo extends JpaRepository<ImageEntity, Long> {
}

