package org.nico.quoted.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void testEquals() {
        Article article1 = new Article("google", "https://www.google.com");
        Article article2 = new Article("Google Search Engine","https://www.Google.com");
        Article article3 = new Article("google","https://www.google.com/");

        assertTrue(article1.equals(article2));
        assertFalse(article1.equals(article3));
    }
}