package org.nico.quoted.domain;

import java.util.Set;

public class Article implements Quotable {
    public static final QuotableType type = QuotableType.ARTICLE;
    private String title;
    private String url;

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return this.url.equalsIgnoreCase(article.url);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getOrigin() {
        return this.url;
    }

    @Override
    public String getType() {
        return type.toString();
    }
}
