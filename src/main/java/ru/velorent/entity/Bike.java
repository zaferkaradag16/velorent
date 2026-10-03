package ru.velorent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Сущность "Велосипед" (таблица bike).
 */
@Entity
@Table(name = "bike")
@NamedQueries({
    @NamedQuery(name = "Bike.findAll",
            query = "SELECT b FROM Bike b ORDER BY b.invNumber"),
    @NamedQuery(name = "Bike.findByStatus",
            query = "SELECT b FROM Bike b WHERE b.status = :status ORDER BY b.invNumber")
})
public class Bike implements Serializable {

    private static final long serialVersionUID = 1L;

    // возможные состояния велосипеда
    public static final String FREE = "FREE";
    public static final String RENTED = "RENTED";
    public static final String REPAIR = "REPAIR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inv_number", nullable = false, length = 10, unique = true)
    private String invNumber;

    @Column(name = "model", nullable = false, length = 60)
    private String model;

    @Column(name = "bike_type", nullable = false, length = 20)
    private String bikeType;

    @Column(name = "frame_size", nullable = false, length = 5)
    private String frameSize;

    @Column(name = "price_per_hour", nullable = false, precision = 8, scale = 2)
    private BigDecimal pricePerHour;

    @Column(name = "status", nullable = false, length = 10)
    private String status = FREE;

    public Bike() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBikeType() {
        return bikeType;
    }

    public void setBikeType(String bikeType) {
        this.bikeType = bikeType;
    }

    public String getFrameSize() {
        return frameSize;
    }

    public void setFrameSize(String frameSize) {
        this.frameSize = frameSize;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // название состояния на русском для вывода на страницах
    public String getStatusName() {
        if (RENTED.equals(status)) {
            return "Выдан";
        }
        if (REPAIR.equals(status)) {
            return "В ремонте";
        }
        return "Свободен";
    }

    @Override
    public String toString() {
        return invNumber + " " + model;
    }
}
