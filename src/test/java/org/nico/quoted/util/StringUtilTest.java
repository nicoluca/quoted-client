package org.nico.quoted.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void isValidURL() {
        assertTrue(StringUtil.isValidURL("https://www.google.com"));
        assertTrue(StringUtil.isValidURL("http://www.google.com"));
        assertTrue(StringUtil.isValidURL("http://google.com"));
        assertTrue(StringUtil.isValidURL("http://google.de/"));

        assertFalse(StringUtil.isValidURL("www.google.com"));
        assertFalse(StringUtil.isValidURL("google.com"));
        assertFalse(StringUtil.isValidURL("google.com/"));
    }

    @Test
    void isValidTitle() {
        assertTrue(StringUtil.isValidTitle("Hello World!"));
        assertFalse(StringUtil.isValidTitle(""));
    }

    @Test
    void isValidAuthor() {
        assertTrue(StringUtil.isValidAuthor("John Doe"));
        assertFalse(StringUtil.isValidAuthor(""));
    }

    @Test
    void isValidCoverPath() {
        assertTrue(StringUtil.isValidCoverPath("src/main/resources/cover.jpg"));
        assertTrue(StringUtil.isValidCoverPath("src/main/resources/cover.png"));
        assertTrue(StringUtil.isValidCoverPath("src/main/resources/cover.jpeg"));
        assertTrue(StringUtil.isValidCoverPath("src/main/resources/cover.gif"));
        assertTrue(StringUtil.isValidCoverPath("src/main/resources/cover.bmp"));
        assertTrue(StringUtil.isValidCoverPath("/src/main/resources/cover.jpg"));
        assertTrue(StringUtil.isValidCoverPath("/Users/jprie/CloudStation/Geschäftlich/JavaKurse/Resources/BookLibrary/spring-start-here-laurentiu-spilca.jpeg"));
        assertTrue(StringUtil.isValidCoverPath("/Users/nico/Desktop/Screenshot 2023-05-09 at 19.09.36.png"));

        assertFalse(StringUtil.isValidCoverPath("src/main/resources/cover"));
        assertFalse(StringUtil.isValidCoverPath("src/main/resources/cover."));
        assertFalse(StringUtil.isValidCoverPath("src/main/resources/cover.jpg/"));
        assertFalse(StringUtil.isValidCoverPath(""));
    }

    @Test
    void isValidISBN() {
        assertTrue(StringUtil.isValidISBN("1234567890"));
        assertTrue(StringUtil.isValidISBN("1234567890123"));
        assertTrue(StringUtil.isValidISBN("123-456-789-0"));
        assertTrue(StringUtil.isValidISBN("123-456-789-0123"));
        assertTrue(StringUtil.isValidISBN("978-1617298691"));

        assertFalse(StringUtil.isValidISBN("TenDigits!"));
        assertFalse(StringUtil.isValidISBN("123456789"));
        assertFalse(StringUtil.isValidISBN("123456789012"));
        assertFalse(StringUtil.isValidISBN("123-456-789"));
        assertFalse(StringUtil.isValidISBN("123-456-789-012"));
        assertFalse(StringUtil.isValidISBN(""));
    }
}