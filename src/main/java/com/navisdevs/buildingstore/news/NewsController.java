package com.navisdevs.buildingstore.news;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor

public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public Page<NewsResponse> getAllNews(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean published,
            @ParameterObject Pageable pageable) {
        return newsService.getAllNews(title, published, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsResponse createNews(@Valid @RequestBody NewsRequest request) {
        return newsService.createNews(request);
    }

    @PutMapping("/{id}")
    public NewsResponse updateNews(@PathVariable Long id,
                                   @Valid @RequestBody NewsRequest request) {
        return newsService.updateNews(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }


    @PatchMapping("/{id}/toggle-publication")
    public NewsResponse togglePublication(@PathVariable Long id) {
        return newsService.togglePublication(id);
    }

}
