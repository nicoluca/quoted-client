package org.nico.quoted.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class RepositoryImplementation<T> implements CRUDRepository<T> {
    private final Class<T> type;
    private final EntityManagerFactory emf;
    private EntityManager em;

    public RepositoryImplementation(Class<T> type, EntityManagerFactory emf) {
        this.type = type;
        this.emf = emf;
    }

    @Override
    public void create(T t) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(t);

            em.getTransaction().commit();
            em.close();

        } catch (IllegalStateException e) {
            log.error("Error while creating Article: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public Optional<T> readById(long id) {
        T t;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            t = em.find(type, id);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("T not found.");
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(t);
    }

    @Override
    public List<T> readAll() {
        List<T> ts = new ArrayList<>();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            TypedQuery<T> query = em.createQuery("select t from " + type.getSimpleName() + " t", type);
            ts = query.getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.warn("No Ts could be retrieved.");
            e.printStackTrace();
        }
        return ts;
    }

    @Override
    public void update(T t) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.merge(t);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while updating t: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T t) {
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            T tToDelete = em.merge(t);
            em.remove(tToDelete);

            em.getTransaction().commit();
            em.close();
        } catch (IllegalStateException e) {
            log.error("Error while deleting T: " + e.getMessage());
            e.printStackTrace();
        }
    }
}