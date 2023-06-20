package org.nico.quoted.service;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.junit.jupiter.api.*;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Article;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleServiceTest {
    private CrudService<Article> articleService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        articleService.readAll().forEach(articleService::delete);
    }


    @Test
    @DisplayName("Read all articles")
    @Disabled("Needs a running server - mock the server through httpservice instead")
    void readAll() {
        assertEquals(0, articleService.readAll().size());

        int count = 10;
        for (int i = 0; i < count; i++) {
            Article article = new Article("Test article", "Test article");
            articleService.create(article);
        }

        assertEquals(count, articleService.readAll().size());
    }
}