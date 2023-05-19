package org.nico.quoted.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nico.quoted.TestUtil;
import org.nico.quoted.domain.Article;
import org.nico.quoted.http.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ArticleSerializationTest {

    private Article article;

    @BeforeEach
    void setup() {
        this.article = new Article("sz.de", "https://www.sz.de");
        this.article.setId(1L);
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //this.article.setLastVisited(timestamp);
    }

    @Test
    void testSerializeArticle() {
        // Gson with custom serializer
        Gson gson = new GsonBuilder().registerTypeAdapter(Article.class, new ArticleSerializer()).create();
        String json = gson.toJson(this.article);
        assertEquals("{\"title\":\"sz.de\",\"url\":\"https://www.sz.de\",\"type\":\"article\"}", json);
    }

    @Test
    void testDeserializeArticle() {
        String json = TestUtil.resourceToString("/serialization/Article.json");
        Gson gson = new GsonBuilder().registerTypeAdapter(Article.class, new ArticleDeserializer()).create();
        Article deserializedArticle = gson.fromJson(json, Article.class);
        assertEquals(this.article, deserializedArticle);
        assertNotNull(deserializedArticle.getLastVisited());
    }

    @Test
    void deserializeList() {
        String json = TestUtil.resourceToString("/serialization/ArticleList.json");
        Gson gson = new GsonBuilder().registerTypeAdapter(Article.class, new ArticleDeserializer()).create();
        JsonUtil<Article> jsonUtil = new JsonUtil<>();
        List<Article> articles = jsonUtil.deserializeList(json, gson, "articles", Article.class);
        assertEquals(4, articles.size());
    }
}
