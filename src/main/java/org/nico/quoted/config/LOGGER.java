package org.nico.quoted.config;

public class LOGGER {
    private static final System.Logger LOGGER = System.getLogger(org.nico.quoted.config.LOGGER.class.getName());
    private static final System.Logger.Level INFO = System.Logger.Level.INFO;
    private static final System.Logger.Level DEBUG = System.Logger.Level.DEBUG;
    private static final System.Logger.Level WARNING = System.Logger.Level.WARNING;
    private static final System.Logger.Level ERROR = System.Logger.Level.ERROR;

    public static void info(String message) {
        LOGGER.log(INFO, message);
    }

    public static void debug(String message) {
        LOGGER.log(DEBUG, message);
    }

    public static void warning(String message) {
        LOGGER.log(WARNING, message);
    }

    public static void error(String message) {
        LOGGER.log(ERROR, message);
    }


}
