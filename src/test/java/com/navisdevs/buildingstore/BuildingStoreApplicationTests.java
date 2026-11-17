package com.navisdevs.buildingstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navisdevs.buildingstore.catalog.CatalogRequest;
import com.navisdevs.buildingstore.content.PageRequest;
import com.navisdevs.buildingstore.news.NewsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BuildingStoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void verifiesModularStructure() {
        ApplicationModules.of(BuildingStoreApplication.class).verify();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void catalogCrudAndFilteringWorks() throws Exception {
        CatalogRequest request = new CatalogRequest(
                "Труба тестовая 25мм",
                "Описание трубы",
                "/api/images/1",
                new BigDecimal("350.00"),
                "Трубы",
                "ГОСТ 3262-75",
                "25мм",
                "Сталь"
        );

        mockMvc.perform(post("/api/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Труба тестовая 25мм"))
                .andExpect(jsonPath("$.category").value("Трубы"));

        // Verify trimmed category filtering
        mockMvc.perform(get("/api/catalog").param("category", "  Трубы  "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name").value("Труба тестовая 25мм"));

        // Verify catalog web view renders properly
        mockMvc.perform(get("/catalog-view"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog/view"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void newsCrudAndPublishedStatusWorks() throws Exception {
        NewsRequest request = new NewsRequest(
                "Тестовая новость",
                "Контент новости",
                "/api/images/1",
                true
        );

        String responseJson = mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Тестовая новость"))
                .andExpect(jsonPath("$.published").value(true))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(responseJson).get("id").asLong();

        mockMvc.perform(patch("/api/news/{id}/toggle-publication", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.published").value(false));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void pageCrudAndSearchWorks() throws Exception {
        PageRequest request = new PageRequest("about-test", "О нас", "<p>Контент</p>");

        mockMvc.perform(post("/api/content")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug").value("about-test"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        mockMvc.perform(get("/api/content/search").param("slug", "about-test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].createdAt").isNotEmpty());

        mockMvc.perform(get("/pages/about-test"))
                .andExpect(status().isOk())
                .andExpect(view().name("content/view"))
                .andExpect(model().attributeExists("page"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void imageUploadAndPublicAccessWorks() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.png", MediaType.IMAGE_PNG_VALUE, new byte[]{1, 2, 3, 4}
        );

        String imageUrl = mockMvc.perform(multipart("/api/images/upload").file(file))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get(imageUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE))
                .andExpect(content().bytes(new byte[]{1, 2, 3, 4}));
    }
}
