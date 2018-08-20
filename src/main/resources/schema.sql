--create database cooking

create table meals
(
	id integer auto_increment,
	name varchar(255),
	meal_type varchar(255),
	recipe varchar(255),
	calories integer,
	primary key(id)
);