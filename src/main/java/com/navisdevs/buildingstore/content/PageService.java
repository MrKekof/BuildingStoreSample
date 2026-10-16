package com.navisdevs.buildingstore.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageService {
    PageResponse getBySlug (String slug);
    PageResponse createPage (PageRequest pageRequest);
    PageResponse updatePage (String slug, PageRequest pageRequest);
    void deletePage (String slug);
    Page<PageResponse> searchPage (String title , Pageable pageable);
}
