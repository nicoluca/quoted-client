package org.nico.quoted.domain;

public class Book implements SourceInterface {
    public static final QuotableType type = QuotableType.BOOK;
    private String title;
    private Author author;
    private String coverPath;

    public Book(String title, Author author, String coverPath) {
        this.title = title;
        this.author = author;
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
        return "Book: " + this.title + " (" + this.author + ")";
    }

    public Author getAuthor() {
        return author;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
}
