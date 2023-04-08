package org.nico.quoted.util;

public class StringUtil {
    public static boolean isValidURL(String url) {
        return url.matches("^(https?|ftp)://[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$");
    }
}
