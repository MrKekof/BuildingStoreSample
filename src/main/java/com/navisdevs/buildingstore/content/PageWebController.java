
package com.navisdevs.buildingstore.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pages")
public class PageWebController {

    private final PageService pageService;

    @GetMapping("/{slug}")
    public String viewPage(@PathVariable String slug, Model model){
        model.addAttribute("page", pageService.getBySlug(slug));
        return "content/view";
    }
}