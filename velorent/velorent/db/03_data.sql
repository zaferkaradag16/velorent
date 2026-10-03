-- Начальное наполнение таблиц
USE velorent;
SET NAMES utf8mb4;

INSERT INTO bike (inv_number, model, bike_type, frame_size, price_per_hour, status) VALUES
    ('В-001', 'Stels Navigator 500',   'Городской', 'M',  150.00, 'RENTED'),
    ('В-002', 'Stels Navigator 500',   'Городской', 'L',  150.00, 'FREE'),
    ('В-003', 'Forward Sporting 27.5', 'Горный',    'M',  200.00, 'FREE'),
    ('В-004', 'Merida Big.Seven 20',   'Горный',    'L',  250.00, 'RENTED'),
    ('В-005', 'Shulz Krabi Coaster',   'Складной',  'S',  180.00, 'FREE'),
    ('В-006', 'Format 5222',           'Шоссейный', 'L',  300.00, 'REPAIR'),
    ('В-007', 'Novatrack Prime 20',    'Детский',   'XS', 100.00, 'FREE');

INSERT INTO rental (bike_id, client_name, client_phone, start_time, end_time, total_cost) VALUES
    (3, 'Петров Сергей',   '+7 922 111-22-33', '2026-09-26 10:00:00', '2026-09-26 13:00:00', 600.00),
    (5, 'Иванова Анна',    '+7 932 444-55-66', '2026-09-27 12:30:00', '2026-09-27 14:30:00', 360.00),
    (1, 'Сидоров Михаил',  '+7 904 777-88-99', '2026-10-02 09:15:00', NULL, NULL),
    (4, 'Кузнецова Ольга', '+7 950 123-45-67', '2026-10-02 11:00:00', NULL, NULL);
