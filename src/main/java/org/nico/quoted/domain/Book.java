package org.nico.quoted.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Book extends Source {
    public static final SourceType type = SourceType.BOOK;
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
        return this.author.toString();
    }

    @Override
    public String toString() {
        return "Book: " + this.getTitle() + " (" + this.author + ")";
    }

}
