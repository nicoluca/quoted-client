package org.nico.quoted.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.nico.quoted.domain.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO Can this be refactored with less boilerpolate code?

@Slf4j
public class ArticleRepository implements CRUDRepository<Article> {
    private final EntityManagerFactory emf;
    private EntityManager em;

    public ArticleRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Article article) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(article);

            em.getTransaction().commit();
            em.close();

        } catch (IllegalStateException e) {
            log.error("Error while creating Article: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Article> readById(long id) {
        Article article;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            article = em.find(Article.class, id);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("Article not found.");
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(article);
    }

    @Override
    public List<Article> readAll() {
        List<Article> articles = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            TypedQuery<Article> query = em.createQuery("select a from Article a", Article.class);
            articles = query.getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("No Articles could be retrieved.");
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public void update(Article article) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(article);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while updating article: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Article article) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Article articleToDelete = em.merge(article);
            em.remove(articleToDelete);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while deleting Article: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

