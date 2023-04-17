package org.nico.quoted.domain;

public class Quote {
    private String text;
    private Source source;

    public Quote(String text, Source source) {
        this.text = text;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public Source getSource() {
        return source;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
