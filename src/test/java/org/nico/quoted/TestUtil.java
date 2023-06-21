package org.nico.quoted;

import org.nico.quoted.domain.Author;
import org.nico.quoted.domain.Book;
import org.nico.quoted.domain.Quote;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;

public class TestUtil {

    public static String resourceToString(String resourcePath) {
        // Base path is src/test/resources
        try {
            Path path = Path.of(Objects.requireNonNull(TestUtil.class.getResource(resourcePath)).getPath());
            return Files.readString(path);
        } catch (IOException e) {
            fail("Could not read resource: " + resourcePath);
            throw new RuntimeException(e);
        }
    }

    public static Book createBook() {
        return Book.builder()
                .title("test")
                .isbn("test")
                .author(
                        new Author("first_name", "last_name")
                )
                .build();
    }

    public static Quote createQuote() {
        Quote quote = Quote.builder()
                .text("test")
                .build();

        quote.setSource(createBook());
        return quote;
    }
}
