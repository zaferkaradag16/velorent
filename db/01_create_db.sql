-- Создание базы данных и пользователя для приложения "Пункт проката велосипедов"
CREATE DATABASE IF NOT EXISTS velorent
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'velo'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Velo2026!';
GRANT ALL PRIVILEGES ON velorent.* TO 'velo'@'localhost';
FLUSH PRIVILEGES;
