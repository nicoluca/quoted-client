package org.nico.quoted.api;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Author;

import java.util.List;

@Slf4j
public class AuthorRepository implements CRUDRepositoryJPA<Author> {

    @Override
    public void create(Author author) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(author);

            em.getTransaction().commit();
            em.close();

            log.info("Author persisted: " + author);
        } catch (IllegalStateException e) {
            log.error("Error while creating author: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Author readById(long id) {
        return null;
    }

    @Override
    public List<Author> readAll() {
        return null;
    }

    @Override
    public void update(Author author) {

    }

    @Override
    public void delete(Author author) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Author authorToDelete = em.merge(author);
            em.remove(authorToDelete);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while deleting author: " + e.getMessage());
            e.printStackTrace();
        }
    }
}