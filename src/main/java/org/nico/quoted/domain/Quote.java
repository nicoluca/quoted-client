package org.nico.quoted.domain;

public class Quote {
    private String text;
    private Quotable source;

    public Quote(String text, Quotable source) {
        this.text = text;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public Quotable getSource() {
        return source;
    }
}
