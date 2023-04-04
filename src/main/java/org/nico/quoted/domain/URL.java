package org.nico.quoted.domain;

import java.util.Set;

public class URL implements Quotable {
    private String url;

    public URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        return this.url.equalsIgnoreCase(url.url);
    }

    @Override
    public Set<Quote> getQuotes() {
        // TODO
        return null;
    }
}
