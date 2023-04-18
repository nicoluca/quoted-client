package org.nico.quoted.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // Needed for JPA
@Getter @Setter
@Entity
public class Book extends Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Author author;
    private String coverPath;

    public Book(String title, Author author, String coverPath) {
        super(title);
        this.author = author;
        this.coverPath = coverPath;
    }

    @Override
    public boolean equals(Object o) {
        // TODO Replace with ID
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.getTitle().equals(book.getTitle())
                && this.author.equals(book.author);
    }

    @Override
    public String getOrigin() {
        return this.author.getFirstName() + " " + this.author.getLastName();
    }


}
