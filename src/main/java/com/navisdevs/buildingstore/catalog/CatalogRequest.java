package com.navisdevs.buildingstore.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CatalogRequest(
        @NotBlank(message = "Название обязательно") String name,
        @NotBlank(message = "Описание обязательно") String description,
        @NotBlank(message = "Ссылка на фото обязательна") String catalogImageUrl,
        @NotNull(message = "Цена обязательна") @Positive BigDecimal price,
        @NotBlank(message = "Категория обязательна") String category,
        @NotBlank(message = "Стандарт (ГОСТ/ISO) обязателен") String standard,
        @NotBlank(message = "Диаметр обязателен") String diameter,
        @NotBlank(message = "Материал обязателен") String material
)
{ }
