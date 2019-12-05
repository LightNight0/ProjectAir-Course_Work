USE project_air;

INSERT INTO aircraft (id, name, type, seats_amount, business_seats_amount)
  VALUES (1, 'Boeing 777', 'Пассажирский', 400, 50);

INSERT INTO crew (id, captain, quantity)
  VALUES (1, 'Михалыч', 3);

INSERT INTO runway (id, name, status)
  VALUES (1, 'Основная 1', 'Свободна'),
         (2, 'Основная 2', 'Свободна');

INSERT INTO route (id, departure_point, arrival_point, flight_time, distance)
  VALUES (1, 'Вильнюс', 'Осло', '09:35', 1291);

INSERT INTO class (id, cost, name)
  VALUES (1, 500, 'Стандарт'),
         (2, 950, 'Бизнес');

INSERT INTO flight (id, departure_time, route, aircraft, runway, crew, days)
  VALUES (1, CURTIME(), 1, 1, 1, 1, '*2**5**');

INSERT INTO person_data (id, age, mail, adress, sex)
  VALUES (1, 23, 'asdfgh@domain.com', 'str. XXX 12-23', 'M');

INSERT INTO passenger (id, full_name, class, place_number, flight, person_data_id)
  VALUES (1, 'Зубенко Михаил Петрович', 1, 43, 1, 1);

INSERT INTO baggage (id, size, place_number, owner)
  VALUES (1, 'Стандарт', 12, 1);

INSERT INTO USER (id, name, login, password, role)
  VALUES (1, 'Main Admin', 'admin', 'admin', 'admin'),
         (1, 'Main Manager', 'manager', 'manager', 'manager');
