package org.nico.quoted.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;

public class DBConfig {
    private static final String DB_NAME = "quote_db";
    private static final PersistenceProvider provider = new org.hibernate.jpa.HibernatePersistenceProvider();
    public static final EntityManagerFactory EMF = provider.createEntityManagerFactory(DB_NAME, null);
}