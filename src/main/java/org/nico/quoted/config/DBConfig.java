package org.nico.quoted.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBConfig {
    public static final String DB_NAME = "quote_db";
    public static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory(DB_NAME);
}