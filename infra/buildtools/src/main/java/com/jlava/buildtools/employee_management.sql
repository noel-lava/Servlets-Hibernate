﻿-- Database: employee_management

-- DROP DATABASE employee_management;

-- CREATE DATABASE IF NOT EXISTS employee_management
--   WITH OWNER = postgres
--        ENCODING = 'UTF8'
--        TABLESPACE = pg_default
--        LC_COLLATE = 'en_PH.UTF-8'
--        LC_CTYPE = 'en_PH.UTF-8'
--        CONNECTION LIMIT = -1;

CREATE TABLE person (
	person_id SERIAL,
	last_name varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	mid_name varchar(20) DEFAULT NULL,
	suffix varchar(4) DEFAULT NULL,
	title varchar(6) DEFAULT NULL,
	birth_date DATE NOT NULL,
	gwa numeric(5,2) DEFAULT NULL,
	date_hired DATE DEFAULT NULL,
	employed boolean NOT NULL,
	street varchar(50) DEFAULT NULL,
	barangay varchar(50) DEFAULT NULL,
	municipality varchar(50) NOT NULL,
	zipcode int,
	PRIMARY KEY (person_id),
	CONSTRAINT FK_person_address_id FOREIGN KEY (address_id)
	REFERENCES address (address_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE contact (
	contact_id SERIAL,
	person_id long NOT NULL,
	contact_type_id long NOT NULL,
	contact_desc varchar(50) NOT NULL,
	PRIMARY KEY(person_id, contact_id),
	CONSTRAINT FK_contact_person_id FOREIGN KEY(person_id)
	REFERENCES person (person_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_contact_type_id FOREIGN KEY(contact_type_id)
	REFERENCES contact_type (contact_type_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE contact_type(
	contact_type_id SERIAL,
	contact_type_code varchar(5) NOT NULL,
	contact_type varchar(30) NOT NULL,
	PRIMARY KEY(contact_type_id)
);

CREATE TABLE role (
	role_id SERIAL,
	role_code varchar(5) NOT NULL,
	role varchar(30) NOT NULL,
	PRIMARY KEY(role_id)
);

CREATE TABLE person_role (
	person_id int NOT NULL,
	role_id int NOT NULL,
	PRIMARY KEY(person_id, role_id),
	CONSTRAINT FK_role_person_id FOREIGN KEY(person_id)
	REFERENCES person (person_id),
	CONSTRAINT FK_role_role_id FOREIGN KEY(role_id)
	REFERENCES role (role_id) 
);