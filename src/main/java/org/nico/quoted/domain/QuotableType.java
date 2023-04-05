package org.nico.quoted.domain;

enum QuotableType {
    ARTICLE("Article"),
    BOOK("Book");

    private final String type;

    QuotableType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
