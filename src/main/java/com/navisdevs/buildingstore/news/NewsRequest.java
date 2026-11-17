package com.navisdevs.buildingstore.news;

import jakarta.validation.constraints.NotBlank;

public record NewsRequest(
        @NotBlank(message = "Заголовок не может быть пустым")
        String title,

        @NotBlank(message = "Контент не может быть пустым")
        String content,

        @NotBlank(message = "Новости должны содержать картинку")
        String newsImageUrl,

        Boolean published
) {
    public NewsRequest(String title, String content, String newsImageUrl) {
        this(title, content, newsImageUrl, false);
    }
}

