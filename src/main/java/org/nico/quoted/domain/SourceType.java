package org.nico.quoted.domain;

enum SourceType {
    ARTICLE("Article"),
    BOOK("Book");

    private final String type;

    SourceType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
