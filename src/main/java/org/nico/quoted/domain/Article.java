package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor // Needed for JPA
@Getter @Setter
@JsonTypeName("article")
public class Article extends Source {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;

    private String url;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Europe/Paris")
    private Timestamp lastVisited;

    public Article(String title, String url) {
        super(title);

        if (title == null || title.isBlank())
            super.setTitle(url);

        this.url = url;
    }

    @Override
    public String toString() {
        if (this.getTitle() == null || this.getTitle().isBlank())
            return this.url;
        if (this.getTitle().equals(this.url))
            return this.getTitle();
        else
            return super.toString();
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
