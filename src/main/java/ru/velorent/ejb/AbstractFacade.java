package ru.velorent.ejb;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Общие операции с сущностями: поиск, добавление, изменение, удаление.
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    protected AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T edit(T entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        EntityManager em = getEntityManager();
        em.remove(em.merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public long count() {
        String jpql = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e";
        return getEntityManager().createQuery(jpql, Long.class).getSingleResult();
    }

    public abstract List<T> findAll();
}
