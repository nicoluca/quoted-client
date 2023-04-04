package org.nico.quoted.domain;

import java.util.Set;

@FunctionalInterface
public interface Quotable {
    Set<Quote> getQuotes();
}
