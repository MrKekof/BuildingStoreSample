package com.navisdevs.buildingstore.news;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    Page<NewsResponse> getAllNews(String title, Boolean published, Pageable pageable);
    NewsResponse createNews (NewsRequest request);
    NewsResponse updateNews (Long id , NewsRequest request);
    void deleteNews (Long id);
    NewsResponse togglePublication (Long id);
}
