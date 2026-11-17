package com.navisdevs.buildingstore.content.internal;

import com.navisdevs.buildingstore.content.PageRequest;
import com.navisdevs.buildingstore.content.PageResponse;
import com.navisdevs.buildingstore.content.PageService;

import com.navisdevs.buildingstore.shared.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PageServiceImpl implements PageService {

    private final PageRepo pageRepo;

    @Override
    public PageResponse getBySlug(String slug) {
        return pageRepo.findBySlug(slug)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Страница " + slug + " не найдена"));
    }

    @Override
    @Transactional
    public PageResponse createPage(PageRequest pageRequest) {
        PageEntity page = new PageEntity();
        page.setSlug(pageRequest.slug());
        page.setTitle(pageRequest.title());
        page.setContent(pageRequest.content());
        return mapToResponse(pageRepo.save(page));
    }

    @Override
    @Transactional
    public PageResponse updatePage(String slug, PageRequest pageRequest) {
        PageEntity page = pageRepo.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Страница " + slug + " не найдена"));
        page.setTitle(pageRequest.title());
        page.setContent(pageRequest.content());
        page.setSlug(pageRequest.slug());
        return mapToResponse(pageRepo.save(page));
    }

    @Override
    @Transactional
    public void deletePage(String slug) {
        PageEntity page = pageRepo.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Страница " + slug + " не найдена"));
        pageRepo.delete(page);
    }

    @Override
    public Page<PageResponse> searchPage(String query, Pageable pageable) {
        Page<PageEntity> entities;
        if (query == null || query.isBlank()) {
            entities = pageRepo.findAll(pageable);
        } else {
            String trimmed = query.trim();
            entities = pageRepo.findBySlugContainingIgnoreCaseOrTitleContainingIgnoreCase(trimmed, trimmed, pageable);
        }
        return entities.map(this::mapToResponse);
    }

    private PageResponse mapToResponse(PageEntity entity) {
        return new PageResponse(
                entity.getSlug(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }
}
