package com.navisdevs.buildingstore.news;

import java.time.LocalDateTime;

public record NewsResponse
        (Long id,
         String title,
         String content,
         String newsImageUrl,
         Boolean published,
         LocalDateTime createdAt)
{

}
