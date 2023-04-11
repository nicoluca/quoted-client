package org.nico.quoted.domain;

public class Article implements SourceInterface {
    public static final QuotableType type = QuotableType.ARTICLE;
    private String title;
    private String url;

    public Article(String title, String url) {
        this.url = url;
        // TODO workaround - should this stay like this?
        if (title == null || title.isBlank())
            this.title = url;
        else
            this.title = title;
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

    public String getUrl() {
        return this.url;
    }

    @Override
    public String getOrigin() {
        return this.url;
    }

    @Override
    public String getType() {
        return type.toString();
    }

    @Override
    public String toString() {
        return "Article: " + this.title + " (" + this.url + ")";
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public void setUrl(String text) {
        this.url = text;
    }
}
