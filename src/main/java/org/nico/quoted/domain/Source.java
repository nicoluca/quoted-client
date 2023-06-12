package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonSubTypes;


import java.util.Set;

@NoArgsConstructor // Needed for JPA
@AllArgsConstructor
@Getter @Setter

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = Article.class, name = "article")
})
public abstract class Source extends Identifiable {

    private String title;

    @JsonIgnore
    private Set<Quote> quotes;

    public Source(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return this.title.equals(source.title);
    }

    @JsonIgnore
    public abstract String getOrigin();

    @Override
    public String toString() {
        return this.getTitle() + " (" + this.getOrigin() + ")";
    }
}
