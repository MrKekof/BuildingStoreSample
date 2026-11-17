package com.navisdevs.buildingstore.news.internal;

import com.navisdevs.buildingstore.news.NewsRequest;
import com.navisdevs.buildingstore.news.NewsResponse;
import com.navisdevs.buildingstore.news.NewsService;
import com.navisdevs.buildingstore.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly= true)
 class NewsServiceImpl implements NewsService {
    private final NewsRepo newsRepo;


    @Override
    public Page<NewsResponse> getAllNews(String title, Boolean published, Pageable pageable) {
        Page<NewsEntity> entities;
        if (published != null) {
            if (title == null || title.isBlank()) {
                entities = newsRepo.findAllByPublished(published, pageable);
            } else {
                entities = newsRepo.findAllByTitleContainingIgnoreCaseAndPublished(title.trim(), published, pageable);
            }
        } else {
            if (title == null || title.isBlank()) {
                entities = newsRepo.findAll(pageable);
            } else {
                entities = newsRepo.findAllByTitleContainingIgnoreCase(title.trim(), pageable);
            }
        }
       return entities.map(this::mapToResponse);
    }

    @Override
    @Transactional
    public NewsResponse createNews(NewsRequest newsRequest) {
        NewsEntity news = new NewsEntity();
        news.setTitle(newsRequest.title());
        news.setContent(newsRequest.content());
        news.setNewsImageUrl(newsRequest.newsImageUrl());
        news.setPublished(Boolean.TRUE.equals(newsRequest.published()));

        NewsEntity saved = newsRepo.save(news);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public NewsResponse updateNews(Long id, NewsRequest request) {
        NewsEntity news = newsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Новость с id " + id + " не найдена"));

        news.setTitle(request.title());
        news.setContent(request.content());
        news.setNewsImageUrl(request.newsImageUrl());
        if (request.published() != null) {
            news.setPublished(request.published());
        }

        return mapToResponse(newsRepo.save(news));
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        if (!newsRepo.existsById(id)) {
            throw new ResourceNotFoundException("Невозможно удалить ," +
                    "новость с id "+id+" не найдено");
        }
        newsRepo.deleteById(id);
    }

    @Override
    @Transactional
    public NewsResponse togglePublication(Long id) {
        NewsEntity news = newsRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Новость с id " + id+" не найдена"));

        news.setPublished(!news.isPublished());
        return mapToResponse(newsRepo.save(news));
    }

    private NewsResponse mapToResponse(NewsEntity newsEntity) {
        return new NewsResponse(
                newsEntity.getId(),
                newsEntity.getTitle(),
                newsEntity.getContent(),
                newsEntity.getNewsImageUrl(),
                newsEntity.isPublished(),
                newsEntity.getCreatedAt()
        );
    }
}

