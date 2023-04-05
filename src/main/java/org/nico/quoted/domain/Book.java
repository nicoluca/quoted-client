package org.nico.quoted.domain;

public class Book implements Quotable {
    public static final QuotableType type = QuotableType.BOOK;
    private String title;
    private Author author;

    // TODO ISBN as id or dedicated id?
    private String isbn;
    private String coverPath;

    public Book(String title, Author author, String isbn, String coverPath) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String getOrigin() {
        return this.author.toString();
    }

    @Override
    public String getType() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        // TODO Replace with ISBN
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.title.equals(book.title)
                && this.author.equals(book.author);
    }

    @Override
    public String toString() {
        return this.title + " (" + this.author + ")";
    }

    public Author getAuthor() {
        return author;
    }
}
