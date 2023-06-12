package org.nico.quoted.service;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nico.quoted.config.Config;
import org.nico.quoted.domain.Article;
import org.nico.quoted.serialization.ArticleDeserializer;
import org.nico.quoted.serialization.ArticleSerializer;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleServiceTest {
    private CrudService<Article> articleService;

    @BeforeEach
    void setUp() {
        JsonDeserializer<Article> articleDeserializer = new ArticleDeserializer();
        JsonSerializer<Article> articleSerializer = new ArticleSerializer();
        articleService = new ServiceImpl<>(Article.class,
                Config.ARTICLES_URL,
                articleSerializer,
                articleDeserializer,
                Config.HTTP_SERVICE);
    }

    @AfterEach
    void tearDown() {
        articleService.readAll().forEach(articleService::delete);
    }


    @Test
    @DisplayName("Read all articles")
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