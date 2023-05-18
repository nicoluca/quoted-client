package org.nico.quoted.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;

public class DBConfigExample {
    private static final String DB_NAME = "quote_db";
    private static final PersistenceProvider provider = new org.hibernate.jpa.HibernatePersistenceProvider();
    public static final EntityManagerFactory EMF = provider.createEntityManagerFactory(DB_NAME, null);
    public static final String SERVER_URL = "http://localhost:8080";
    public static final String BASIC_AUTH_USERNAME = "USERNAME";
    public static final String BASIC_AUTH_PASSWORD = "PASSWORD";
}