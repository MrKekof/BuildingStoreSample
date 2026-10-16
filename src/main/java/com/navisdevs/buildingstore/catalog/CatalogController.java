package com.navisdevs.buildingstore.catalog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping
    public Page<CatalogResponse> getCatalog(
            @RequestParam(required = false)
            String category,
            @ParameterObject Pageable pageable) {
        return catalogService.getCatalog(category, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CatalogResponse createProduct(@Valid @RequestBody CatalogRequest catalogRequest) {
        return catalogService.createProduct(catalogRequest);
    }


    @PutMapping("/{id}")
    public CatalogResponse updateProduct(@PathVariable Long id ,
                                         @Valid @RequestBody CatalogRequest catalogRequest) {
        return catalogService.updateProduct(id, catalogRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        catalogService.deleteProduct(id);
    }
}
