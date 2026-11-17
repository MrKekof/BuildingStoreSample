package com.navisdevs.buildingstore.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminWebController {

    @GetMapping("/")
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/admin/catalog")
    public String catalog(Model model) {
        model.addAttribute("title", "Catalog Management");
        return "admin/catalog";
    }

    @GetMapping("/admin/news")
    public String news(Model model) {
        model.addAttribute("title", "News Management");
        return "admin/news";
    }

    @GetMapping("/admin/pages")
    public String pages(Model model) {
        model.addAttribute("title", "Page Management");
        return "admin/pages";
    }
}

