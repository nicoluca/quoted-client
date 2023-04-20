package org.nico.quoted.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AuthorRepository implements CRUDRepository<Author> {
    private final EntityManagerFactory emf;
    private EntityManager em;

    public AuthorRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Author author) {
        try {
            em = emf.createEntityManager();
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
    public Optional<Author> readById(long id) {
        Author author;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            author = em.find(Author.class, id);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while reading author by id: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> readAll() {
        List<Author> authors = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
            authors = query.getResultList();

            em.getTransaction().commit();
            em.close();

            return authors;
        } catch (IllegalStateException e) {
            log.error("Error while reading all authors: " + e.getMessage());
            e.printStackTrace();
        }

        return authors;
    }

    @Override
    public void update(Author author) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(author);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while updating author: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Author author) {
        try {
            em = emf.createEntityManager();
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