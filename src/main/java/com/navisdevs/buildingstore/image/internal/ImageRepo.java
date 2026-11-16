package com.navisdevs.buildingstore.image.internal;

import org.springframework.data.jpa.repository.JpaRepository;

interface ImageRepo extends JpaRepository<ImageEntity, Long> {
}
