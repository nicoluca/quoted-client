package org.nico.quoted.repository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.config.BackendConstants;
import org.nico.quoted.domain.Quote;

import java.util.List;
import java.util.Optional;

@Slf4j
public class QuoteRepository implements CRUDRepositoryJPA<Quote> {
    private static QuoteRepository instance;
    private EntityManager em;

    public static QuoteRepository getInstance() {
        if (instance == null)
            instance = new QuoteRepository();
        return instance;
    }

    @Override
    public void create(Quote quote) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(quote);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while creating quote: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Quote> readById(long id) {
        Quote quote;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            quote = em.find(Quote.class, id);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while reading quote by id: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(quote);
    }

    @Override
    public List<Quote> readAll() {
        return BackendConstants.defaultQuotes();
    }

    @Override
    public void update(Quote quote) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(quote);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while updating quote: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Quote quote) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Quote quoteToDelete = em.merge(quote);
            em.remove(quoteToDelete);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while deleting quote: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
