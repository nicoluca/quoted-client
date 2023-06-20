package org.nico.quoted.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Needed for JPA
@Getter @Setter
@JsonTypeName("book")
public class Book extends Source {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id;

    @JsonIncludeProperties({"firstName", "lastName"})
    private Author author;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String coverPath; // TODO Currently unused, intention to be used in future export to MD feature

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String isbn; // TODO Currently unused, intention to be used in future export to MD feature

    public Book(String title, Author author) {
        super(title);
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.getTitle().equalsIgnoreCase(book.getTitle())
                && this.author.equals(book.author);
    }

    @Override
    public String getOrigin() {
        return this.author.getFirstName() + " " + this.author.getLastName();
    }
}
