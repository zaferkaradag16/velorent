# Пункт проката велосипедов (velorent)

Учебное веб-приложение на Jakarta EE 10 по модулю 4 «Технологии конструирования программного обеспечения», вариант 5.

Используется: JSP + JSTL, сервлеты, EJB (Stateless), JPA (EclipseLink), MySQL 8.0, сервер приложений GlassFish 7.0.24, JDK 21.

## Структура репозитория

- `src/main/java/ru/velorent/entity` – сущности JPA (Bike, Rental)
- `src/main/java/ru/velorent/ejb` – сессионные компоненты EJB
- `src/main/java/ru/velorent/web` – сервлеты-контроллеры
- `src/main/webapp` – JSP-страницы, JSP-сегменты, web.xml, стили
- `src/main/resources/META-INF/persistence.xml` – настройка JPA
- `db` – SQL-скрипты создания и наполнения базы
- `target` – скомпилированные классы и архив `velorent.war`

## Запуск

```bash
# база данных
cd db
sudo mysql < 01_create_db.sql
mysql -u velo -p < 02_schema.sql
mysql -u velo -p < 03_data.sql

# пул соединений и ресурс на сервере
asadmin create-jdbc-connection-pool --datasourceclassname org.mariadb.jdbc.MariaDbDataSource \
  --restype javax.sql.DataSource \
  --property "user=velo:password=Velo2026!:url=jdbc\\:mysql\\://localhost\\:3306/velorent" VelorentPool
asadmin create-jdbc-resource --connectionpoolid VelorentPool jdbc/velorent

# развертывание
asadmin deploy target/velorent.war
```

Адрес приложения: http://localhost:8080/velorent/
