package org.nico.quoted.util;

public class StringUtil {
    public static boolean isValidURL(String url) {
        return url.matches("^(https?|ftp)://[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$");
    }

    public static boolean isValidTitle(String title) {
        // 2 letters minimum, allow spaces, commas, apostrophes, etc.
        return title.matches("^[A-Za-z0-9\\s\\-',.:;()&!]{2,}$");
    }

    public static boolean isValidAuthor(String author) {
        // 2 letters minimum, only letters, spaces, dashes, apostrophes, periods
        return author.matches("^[A-Za-z.\\s\\-']{2,}$");
    }

    public static boolean isValidCoverPath(String text) {
        // Can start with slash, character/number sections followed by slash, should end with .jpg, .png, .jpeg, .gif, .bmp
        return text.matches("^/?[\\u00C0-\\u017FA-Za-z0-9\\-]+(/[\\u00C0-\\u017F.\\sA-Za-z0-9\\-]+)*\\.(jpg|png|jpeg|gif|bmp)$");
    }

    public static boolean isValidISBN(String text) {
        // ISBN can be 10 or 13 digits, allow dashes in between
        return text.matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$");
    }
}
