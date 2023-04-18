package org.nico.quoted.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.config.BackendConfig;
import org.nico.quoted.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO Can this be refactored with less boilerpolate code?

@Slf4j
public class BookRepository implements CRUDRepository<Book> {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(BackendConfig.DB_NAME);
    private EntityManager em;


    @Override
    public void create(Book book) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(book);

            em.getTransaction().commit();
            em.close();

        } catch (IllegalStateException e) {
            log.error("Error while creating book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Book> readById(long id) {
        Book book;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            book = em.find(Book.class, id);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("Book not found.");
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> readAll() {
        List<Book> books = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            TypedQuery<Book> query = em.createQuery("select a from Book a", Book.class);
            books = query.getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("No books could be retrieved.");
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void update(Book book) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(book);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while updating book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Book book) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Book bookToDelete = em.merge(book);
            em.remove(bookToDelete);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while deleting book: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
