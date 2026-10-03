package ru.velorent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Сущность "Выдача велосипеда" (таблица rental).
 */
@Entity
@Table(name = "rental")
@NamedQueries({
    @NamedQuery(name = "Rental.findAll",
            query = "SELECT r FROM Rental r ORDER BY r.startTime DESC"),
    @NamedQuery(name = "Rental.findOpen",
            query = "SELECT r FROM Rental r WHERE r.endTime IS NULL ORDER BY r.startTime"),
    @NamedQuery(name = "Rental.findByBike",
            query = "SELECT r FROM Rental r WHERE r.bike.id = :bikeId ORDER BY r.startTime DESC"),
    @NamedQuery(name = "Rental.totalIncome",
            query = "SELECT SUM(r.totalCost) FROM Rental r WHERE r.endTime IS NOT NULL")
})
public class Rental implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bike_id", nullable = false)
    private Bike bike;

    @Column(name = "client_name", nullable = false, length = 80)
    private String clientName;

    @Column(name = "client_phone", nullable = false, length = 20)
    private String clientPhone;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "total_cost", precision = 8, scale = 2)
    private BigDecimal totalCost;

    public Rental() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    // выдача считается открытой, пока велосипед не вернули
    public boolean isOpen() {
        return endTime == null;
    }

    // даты в удобном для страницы виде (EL не умеет форматировать LocalDateTime)
    public String getStartText() {
        return startTime == null ? "" : startTime.format(FMT);
    }

    public String getEndText() {
        return endTime == null ? "—" : endTime.format(FMT);
    }
}
