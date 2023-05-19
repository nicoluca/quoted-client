package org.nico.quoted.repository;

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
    @DisplayName("Create article and confirm it is saved in backend")
    void createAndRetrieve() {
        Article article = new Article("Test article", "test-article.com");
        article = articleService.create(article);

        Optional<Article> articleOptional = articleService.readById(article.getId());
        assert articleOptional.isPresent();
        assertEquals(article, articleOptional.get());
    }

    @Test
    @DisplayName("Read article by id")
    void readById() {
        assertEquals(Optional.empty(), articleService.readById(1L));
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

    @Test
    void update() {
        Article article = new Article("Test article", "Test article");
        article = articleService.create(article);

        String newTitle = "New title";
        article.setTitle(newTitle);
        article = articleService.update(article);

        Optional<Article> articleOptional = articleService.readById(article.getId());
        assert articleOptional.isPresent();
        assertEquals(newTitle, articleOptional.get().getTitle());
    }

    @Test
    void delete() {
        Article article = new Article("Test article", "Test article");
        article = articleService.create(article);
        articleService.delete(article);

        assertEquals(Optional.empty(), articleService.readById(article.getId()));
    }

    @Test
    void testLastVisited() {
        Article article = new Article("Test article", "Test article");
        article = articleService.create(article);

        Optional<Article> articleOptional = articleService.readById(article.getId());
        assert articleOptional.isPresent();
        assertEquals(article.getLastVisited(), articleOptional.get().getLastVisited());

        LocalDate today = LocalDate.now();
        LocalDate lastVisited = article.getLastVisited().toLocalDateTime().toLocalDate();
        assertEquals(today, lastVisited);
    }
}