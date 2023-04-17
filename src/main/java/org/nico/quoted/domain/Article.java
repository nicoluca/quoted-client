package org.nico.quoted.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Article extends Source {
    public static final SourceType type = SourceType.ARTICLE;
    private String url;

    public Article(String title, String url) {
        // TODO Keep URL as title substitute?
        super(title);

        if (title == null || title.isBlank())
            super.setTitle(url);

        this.url = url;
    }

    @Override
    public String toString() {
        // TODO Substitute title with '-' if not present?
        return "Article: " + this.getTitle() + " (" + this.url + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return this.url.equalsIgnoreCase(article.url);
    }

    @Override
    public String getOrigin() {
        return this.url;
    }

}
