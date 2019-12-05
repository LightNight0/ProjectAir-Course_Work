CREATE DATABASE IF NOT EXISTS Project_Air;
USE Project_Air;


CREATE TABLE IF NOT EXISTS person_data
(
  id int AUTO_INCREMENT PRIMARY KEY,
  age int CHECK (age >= 18) NULL,
  mail varchar(40) NULL,
  adress varchar(40) NULL,
  sex char CHECK (sex IN ('M', 'W')) NULL
);


CREATE TABLE IF NOT EXISTS user (
  id int AUTO_INCREMENT PRIMARY KEY,
  name varchar(20) NOT NULL,
  login varchar(20) NOT NULL,
  password varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  person_data_id int NULL,
  CONSTRAINT user_person_data_fk
  FOREIGN KEY (person_data_id) REFERENCES person_data (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS aircraft (
  id int AUTO_INCREMENT PRIMARY KEY,
  name varchar(20) NOT NULL,
  type varchar(20) NULL,
  seats_amount int DEFAULT '0' NOT NULL,
  business_seats_amount int DEFAULT '0' NOT NULL
);


CREATE TABLE IF NOT EXISTS class (
  id int AUTO_INCREMENT PRIMARY KEY,
  name varchar(20) NOT NULL,
  cost int NULL
);


CREATE TABLE IF NOT EXISTS runway (
  id int AUTO_INCREMENT PRIMARY KEY,
  name varchar(20) NOT NULL,
  status varchar(20) DEFAULT 'Свободна' NOT NULL
);


CREATE TABLE IF NOT EXISTS route (
  id int AUTO_INCREMENT PRIMARY KEY,
  departure_point varchar(20) NOT NULL,
  arrival_point varchar(20) NOT NULL,
  flight_time time NULL,
  distance int NULL
);


CREATE TABLE IF NOT EXISTS crew (
  id int AUTO_INCREMENT PRIMARY KEY,
  captain varchar(20) NOT NULL,
  quantity int NULL
);


CREATE TABLE IF NOT EXISTS flight (
  id int AUTO_INCREMENT PRIMARY KEY,
  departure_time time NOT NULL,
  route int NOT NULL,
  aircraft int NOT NULL,
  runway int NOT NULL,
  crew int NOT NULL,
  days varchar(10) NULL,
  CONSTRAINT flight_aircraft_fk
  FOREIGN KEY (aircraft) REFERENCES aircraft (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT flight_route_fk
  FOREIGN KEY (route) REFERENCES route (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT flight_runway_fk
  FOREIGN KEY (runway) REFERENCES runway (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT flight_crew_fk
  FOREIGN KEY (crew) REFERENCES crew (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS passenger (
  id int AUTO_INCREMENT PRIMARY KEY,
  full_name varchar(50) NOT NULL,
  class int NOT NULL,
  place_number int NULL,
  flight int NOT NULL,
  person_data_id int NULL,
  CONSTRAINT passenger_person_data_fk
  FOREIGN KEY (person_data_id) REFERENCES person_data (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT passenger_class_fk
  FOREIGN KEY (class) REFERENCES class (id)
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT passenger_flight_fk
  FOREIGN KEY (flight) REFERENCES flight (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS baggage (
  id int AUTO_INCREMENT PRIMARY KEY,
  size varchar(20) NULL,
  place_number int NULL,
  owner int NOT NULL,
  CONSTRAINT baggage_passenger_fk
  FOREIGN KEY (owner) REFERENCES passenger (id)
  ON UPDATE CASCADE ON DELETE CASCADE
);
