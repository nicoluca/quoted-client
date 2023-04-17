package org.nico.quoted.api;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.nico.quoted.config.BackendConfig;

public interface CRUDRepositoryJPA<T> extends CRUDRepository<T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(BackendConfig.DB_NAME);
}
