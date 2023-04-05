package org.nico.quoted.config;
import org.nico.quoted.domain.*;

import java.util.ArrayList;
import java.util.List;

public class BackendConstants {
    public static final List<Quotable> defaultSources() {
        return List.of(
                new Article("Diesen Text schrieb der US-Reporter Evan Gershkovich, bevor er in Russland verhaftet wurde",
                        "https://www.zeit.de/2023/15/russland-wirtschaft-energie-export-inflation"),
                new Book("Der Herr der Ringe", new Author("J.r.r", "Tolkien"), null, null),
                new Book("Der kleine Hobbit", new Author("J.r.r", "Tolkien"), null, null)
        );
    }

    public static List<Quote> defaultQuotes() {
        return List.of(
                new Quote("Lorem ipsum", defaultSources().get(0)),
                new Quote("Dolor sit amet", defaultSources().get(1)),
                new Quote("Consectetur adipiscing elit", defaultSources().get(2))
        );
    }

}
