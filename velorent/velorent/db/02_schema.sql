-- Таблицы базы данных velorent
USE velorent;
SET NAMES utf8mb4;

DROP TABLE IF EXISTS rental;
DROP TABLE IF EXISTS bike;

-- Велосипеды, которые есть в пункте проката
CREATE TABLE bike (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    inv_number     VARCHAR(10)   NOT NULL UNIQUE,
    model          VARCHAR(60)   NOT NULL,
    bike_type      VARCHAR(20)   NOT NULL,
    frame_size     VARCHAR(5)    NOT NULL,
    price_per_hour DECIMAL(8, 2) NOT NULL,
    status         VARCHAR(10)   NOT NULL DEFAULT 'FREE'
) ENGINE = InnoDB;

-- Выдачи велосипедов клиентам
CREATE TABLE rental (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    bike_id      INT           NOT NULL,
    client_name  VARCHAR(80)   NOT NULL,
    client_phone VARCHAR(20)   NOT NULL,
    start_time   DATETIME      NOT NULL,
    end_time     DATETIME      NULL,
    total_cost   DECIMAL(8, 2) NULL,
    CONSTRAINT fk_rental_bike FOREIGN KEY (bike_id) REFERENCES bike (id)
) ENGINE = InnoDB;
