package org.nico.quoted.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestConfig;
import org.nico.quoted.domain.Article;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArticleRepositoryTest {
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleRepository = new ArticleRepository(TestConfig.TEST_EMF);
    }

    @AfterEach
    void tearDown() {
        articleRepository.readAll().forEach(articleRepository::delete);
    }

    @Test
    void create() {
        Article article = new Article("Test article", "Test article");
        articleRepository.create(article);

        assertEquals(article, articleRepository.readById(article.getId()).get());
    }

    @Test
    void readById() {
        assertEquals(Optional.empty(), articleRepository.readById(1L));
    }

    @Test
    void readAll() {
        assertEquals(0, articleRepository.readAll().size());

        int count = 10;
        for (int i = 0; i < count; i++) {
            Article article = new Article("Test article", "Test article");
            articleRepository.create(article);
        }

        assertEquals(count, articleRepository.readAll().size());
    }

    @Test
    void update() {
        Article article = new Article("Test article", "Test article");
        articleRepository.create(article);

        String newTitle = "New title";
        article.setTitle(newTitle);
        articleRepository.update(article);

        assertEquals(newTitle, articleRepository.readById(article.getId()).get().getTitle());
    }

    @Test
    void delete() {
        Article article = new Article("Test article", "Test article");
        articleRepository.create(article);

        articleRepository.delete(article);

        assertEquals(Optional.empty(), articleRepository.readById(article.getId()));
    }
}