package com.navisdevs.buildingstore.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalog-view")
@RequiredArgsConstructor
public class CatalogWebController {
    private final CatalogService catalogService;

    @GetMapping
    public String viewCatalog(@RequestParam(required = false) String category, Model model) {
        model.addAttribute("products", catalogService.getCatalog(category, Pageable.unpaged()).getContent());
        model.addAttribute("selectedCategory", category);
        return "catalog/view";
    }
}

