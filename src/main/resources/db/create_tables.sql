CREATE TABLE seat_class (
  code varchar(30) PRIMARY KEY,
  name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE airplane_type (
  code varchar(30) PRIMARY KEY,
  name varchar(50) NOT NULL UNIQUE
);

CREATE TABLE seat (
    id UUID PRIMARY KEY,
    class_code varchar(30) NOT NULL,
    airplane_model_code varchar(30) NOT NULL,
    code varchar(30) NOT NULL,
    FOREIGN KEY (class_code) REFERENCES seat_class(code)
);

CREATE TABLE airplane_model (
    code varchar(30) PRIMARY KEY,
    seat_number INTEGER,
    type_code varchar(30),
    maximum_flight_range INTEGER,
    length REAL,
    path_length INTEGER,
    FOREIGN KEY (type_code) REFERENCES airplane_type(code)
);


ALTER TABLE seat
ADD CONSTRAINT fk_airplane_model_code
FOREIGN KEY (airplane_model_code)
REFERENCES airplane_model(code);

CREATE TABLE airplane (
    id UUID PRIMARY KEY,
    code varchar(30) NOT NULL,
    model_code varchar(30) NOT NULL,
    airline_id UUID,
    manufacture_date DATE,
    number_of_flights INTEGER,
    FOREIGN KEY (model_code) REFERENCES airplane_model(code)

);

CREATE TABLE route (
    id UUID PRIMARY KEY,
    code varchar(30) NOT NULL,
    source_airport_id UUID NOT NULL,
    target_airport_id UUID NOT NULL,
    flight_duration INTEGER
);

CREATE TABLE flight (
    id UUID PRIMARY KEY,
    code varchar(30) NOT NULL,
    route_id UUID NOT NULL,
    airplane_id UUID NOT NULL
);

ALTER TABLE flight
ADD CONSTRAINT fk_route_id
FOREIGN KEY (route_id)
REFERENCES route(id);

ALTER TABLE flight
ADD CONSTRAINT fk_airplane_id
FOREIGN KEY (airplane_id)
REFERENCES airplane(id);

CREATE TABLE tariff (
    id uuid PRIMARY KEY,
    airline_id uuid NOT NULL,
    name varchar(30) NOT NULL,
    include_hand_baggage boolean DEFAULT TRUE,
    hand_baggage_weight real,
    include_baggage boolean NOT NULL,
    baggage_weight real,
    include_ticket_exchange boolean NOT NULL,
    include_ticket_refund boolean NOT NULL
);

CREATE TABLE ticket (
    id uuid PRIMARY KEY,
    passenger_id uuid NOT NULL,
    flight_id uuid NOT NULL,
    order_dateTime timestamp NOT NULL,
    price real NOT NULL,
    seat_id uuid,
    tariff_id uuid NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flight(id),
    FOREIGN KEY (seat_id) REFERENCES seat(id),
    FOREIGN KEY (tariff_id) REFERENCES tariff(id)
);

CREATE TABLE passenger (
    id uuid PRIMARY KEY,
    name varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    father_name varchar(50),
    phone_number varchar(16),
    email varchar(50),
    birth_date date NOT NULL,
    registration_date date,
    sex varchar(6) NOT NULL
);

ALTER TABLE ticket
ADD CONSTRAINT fk_passenger_id
FOREIGN KEY (passenger_id)
REFERENCES passenger(id);

CREATE TABLE document_type (
    code varchar(100) PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE identity_document (
    id uuid PRIMARY KEY,
    passenger_id uuid NOT NULL,
    document_type_code varchar(100) NOT NULL,
    number varchar(30) NOT NULL,
    expiration_date date,
    issue_date date,
    FOREIGN KEY (passenger_id) REFERENCES passenger(id),
    FOREIGN KEY (document_type_code) REFERENCES document_type(code)
);

CREATE TABLE timetable (
    id uuid PRIMARY KEY,
    flight_id uuid NOT NULL,
    departure_datetime timestamp,
    arrival_datetime timestamp,
    registration_start_datetime timestamp,
    registration_end_datetime timestamp,
    delayed boolean DEFAULT FALSE,
    FOREIGN KEY (flight_id) REFERENCES flight(id)
);

CREATE TABLE airline (
    id uuid PRIMARY KEY,
    name varchar(30) NOT NULL UNIQUE,
    site varchar(50),
    logo bytea,
    country_code varchar(30) NOT NULL
);

ALTER TABLE airplane
ADD CONSTRAINT fk_airline_id
FOREIGN KEY (airline_id)
REFERENCES airline(id);

CREATE TABLE country (
    code varchar(30) PRIMARY KEY,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE city (
    code varchar(50) PRIMARY KEY,
    name varchar(30) NOT NULL UNIQUE,
    country_code varchar(50) NOT NULL,
    FOREIGN KEY (country_code) REFERENCES country(code)
);

ALTER TABLE airline
ADD CONSTRAINT fk_country_code
FOREIGN KEY (country_code)
REFERENCES country(code);

CREATE TABLE airport(
    id uuid PRIMARY KEY,
    city_code varchar(50) NOT NULL,
    name varchar(30) NOT NULL,
    code varchar(30) NOT NULL,
    is_international boolean,
    address varchar(50),
    phone_number varchar(16),
    FOREIGN KEY (city_code) REFERENCES city(code)
);

ALTER TABLE route
ADD CONSTRAINT fk_airport_id
FOREIGN KEY (source_airport_id)
REFERENCES airport(id);

ALTER TABLE route
ADD CONSTRAINT fk_airport_2_id
FOREIGN KEY (target_airport_id)
REFERENCES airport;

CREATE TABLE citizenship (
    passenger_id uuid REFERENCES passenger,
    country_code varchar REFERENCES country,
    PRIMARY KEY (passenger_id, country_code)
);

