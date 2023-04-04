package org.nico.quoted.domain;

import java.util.Set;

public class Article implements Quotable {
    private String url;

    public Article(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return this.url.equalsIgnoreCase(article.url);
    }

    @Override
    public Set<Quote> getQuotes() {
        // TODO
        return null;
    }
}
