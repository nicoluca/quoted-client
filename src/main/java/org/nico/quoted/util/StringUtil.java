package org.nico.quoted.util;

public class StringUtil {
    public static boolean isValidURL(String url) {
        return url.matches("^(https?|ftp)://[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$");
    }

    public static boolean isValidTitle(String title) {
        // 2 letters minimum, allow spaces, commas, apostrophes, etc.
        return title.matches("^[A-Za-z0-9\\s\\-',.:;()&]{2,}$");
    }

    public static boolean isValidAuthor(String author) {
        // 2 letters minimum, only letters, spaces, dashes, apostrophes, periods
        return author.matches("^[A-Za-z.\\s\\-']{2,}$");
    }
}
