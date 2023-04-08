package org.nico.quoted.domain;

public class Quote {
    private String text;
    private SourceInterface source;

    public Quote(String text, SourceInterface source) {
        this.text = text;
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public SourceInterface getSource() {
        return source;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(SourceInterface source) {
        this.source = source;
    }
}
