package com.navisdevs.buildingstore.catalog.internal;

import com.navisdevs.buildingstore.shared.MainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "catalog")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

class CatalogEntity extends MainEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String catalogImageUrl;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String standard;

    @Column(nullable = false)
    private String diameter;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String category;

}
