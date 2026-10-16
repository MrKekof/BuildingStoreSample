package com.navisdevs.buildingstore.image;

public record ImageResponse(
        Long id,
        String name,
        String contentType,
        byte[] data
) {}
