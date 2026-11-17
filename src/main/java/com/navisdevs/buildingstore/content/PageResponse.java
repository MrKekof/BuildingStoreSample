package com.navisdevs.buildingstore.content;

import java.time.LocalDateTime;

public record PageResponse(
        String slug,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public PageResponse(String slug, String title, String content) {
        this(slug, title, content, null);
    }
}

