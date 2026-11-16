package com.navisdevs.buildingstore.content;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{slug}")
    public PageResponse getPage(@PathVariable String slug){
        return pageService.getBySlug(slug);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PageResponse createPage(@Valid @RequestBody PageRequest pageRequest){
        return pageService.createPage(pageRequest);
    }

    @PutMapping("/{slug}")
    public PageResponse updatePage(@PathVariable String slug,
                                   @Valid @RequestBody PageRequest pageRequest){
        return pageService.updatePage(slug, pageRequest);
    }

    @DeleteMapping("/{slug}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePage(@PathVariable String slug){
        pageService.deletePage(slug);
    }

    @GetMapping("/search")
    public Page<PageResponse> searchPage(@RequestParam(required = false) String slug,
                                         @ParameterObject Pageable pageable){
        return pageService.searchPage(slug, pageable);
    }

}
