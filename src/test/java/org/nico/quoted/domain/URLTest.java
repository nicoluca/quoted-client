package org.nico.quoted.domain;

import org.junit.jupiter.api.Test;
import org.nico.quoted.domain.URL;

import static org.junit.jupiter.api.Assertions.*;

class URLTest {

    @Test
    void testEquals() {
        URL url1 = new URL("https://www.google.com");
        URL url2 = new URL("https://www.Google.com");
        URL url3 = new URL("https://www.google.com/");

        assertTrue(url1.equals(url2));
        assertFalse(url1.equals(url3));
    }
}