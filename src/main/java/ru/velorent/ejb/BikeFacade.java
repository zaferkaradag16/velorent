package ru.velorent.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import ru.velorent.entity.Bike;

/**
 * Сессионный компонент для работы с велосипедами.
 */
@Stateless
public class BikeFacade extends AbstractFacade<Bike> {

    @PersistenceContext(unitName = "velorentPU")
    private EntityManager em;

    public BikeFacade() {
        super(Bike.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Bike> findAll() {
        return em.createNamedQuery("Bike.findAll", Bike.class).getResultList();
    }

    // только велосипеды, которые сейчас можно выдать
    public List<Bike> findFree() {
        return em.createNamedQuery("Bike.findByStatus", Bike.class)
                .setParameter("status", Bike.FREE)
                .getResultList();
    }

    public long countByStatus(String status) {
        return em.createQuery("SELECT COUNT(b) FROM Bike b WHERE b.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }
}
