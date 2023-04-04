package org.nico.quoted.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuoteTest {
    private Quote quote, quote2, quote3;

    @BeforeEach
    void setUp() {
        quote = new Quote("Hello World!",
                new Book("The Hobbit", new Author("J.R.R.", "Tolkien"), "12345", "abcd"));
        quote2 = new Quote("Hello World!",
                new Book("The Hobbit", new Author("J.R.R.", "Tolkien"), "12345", "abcd"));
        quote3 = new Quote("Hello World!", new Article("https://www.google.com"));
    }

    @Test
    void getText() {
        assert quote.getText().equals("Hello World!");
        assert quote2.getText().equals("Hello World!");
        assert quote3.getText().equals("Hello World!");
    }

    @Test
    void getSource() {
        System.out.println(quote.getSource());
        System.out.println(quote2.getSource());
        assert quote.getSource().equals(quote2.getSource());
        assert !quote.getSource().equals(quote3.getSource());
    }
}