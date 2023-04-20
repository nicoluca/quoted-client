package org.nico.quoted;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TestConfig {
    public static final String TEST_DB_NAME = "quote_db_test";
    public static final EntityManagerFactory TEST_EMF = Persistence.createEntityManagerFactory(TEST_DB_NAME);
}
