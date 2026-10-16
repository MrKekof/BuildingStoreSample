package com.navisdevs.buildingstore.catalog;

import java.math.BigDecimal;

public record CatalogResponse(
        Long id,
        String name,
        String description,
        String catalogImageUrl,
        BigDecimal price,
        String standard,
        String material,
        String diameter ,
        String category
)
{
}
