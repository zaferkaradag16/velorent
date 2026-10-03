package ru.velorent.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import ru.velorent.entity.Bike;
import ru.velorent.entity.Rental;

/**
 * Сессионный компонент для работы с выдачами велосипедов.
 */
@Stateless
public class RentalFacade extends AbstractFacade<Rental> {

    @PersistenceContext(unitName = "velorentPU")
    private EntityManager em;

    public RentalFacade() {
        super(Rental.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Rental> findAll() {
        return em.createNamedQuery("Rental.findAll", Rental.class).getResultList();
    }

    public List<Rental> findOpen() {
        return em.createNamedQuery("Rental.findOpen", Rental.class).getResultList();
    }

    public List<Rental> findByBike(Integer bikeId) {
        return em.createNamedQuery("Rental.findByBike", Rental.class)
                .setParameter("bikeId", bikeId)
                .getResultList();
    }

    public BigDecimal totalIncome() {
        BigDecimal sum = em.createNamedQuery("Rental.totalIncome", BigDecimal.class)
                .getSingleResult();
        return sum == null ? BigDecimal.ZERO : sum;
    }

    /**
     * Выдача велосипеда клиенту. Велосипед получает статус "Выдан".
     */
    public void openRental(Integer bikeId, String clientName, String clientPhone) {
        Bike bike = em.find(Bike.class, bikeId);
        if (bike == null || !Bike.FREE.equals(bike.getStatus())) {
            throw new IllegalStateException("Велосипед недоступен для выдачи");
        }
        Rental rental = new Rental();
        rental.setBike(bike);
        rental.setClientName(clientName);
        rental.setClientPhone(clientPhone);
        rental.setStartTime(LocalDateTime.now().withSecond(0).withNano(0));
        em.persist(rental);
        bike.setStatus(Bike.RENTED);
    }

    /**
     * Возврат велосипеда. Стоимость считается за каждый начатый час.
     */
    public void closeRental(Integer rentalId) {
        Rental rental = em.find(Rental.class, rentalId);
        if (rental == null || !rental.isOpen()) {
            return;
        }
        LocalDateTime end = LocalDateTime.now().withSecond(0).withNano(0);
        long minutes = Duration.between(rental.getStartTime(), end).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);
        BigDecimal cost = rental.getBike().getPricePerHour()
                .multiply(BigDecimal.valueOf(hours))
                .setScale(2, RoundingMode.HALF_UP);
        rental.setEndTime(end);
        rental.setTotalCost(cost);
        rental.getBike().setStatus(Bike.FREE);
    }
}
