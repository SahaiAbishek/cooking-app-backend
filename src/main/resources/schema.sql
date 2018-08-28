--create database cooking

create table meals
(
	id integer auto_increment,
 	name varchar(255),
	meal_type varchar(255), -- breakfast,lunch or dinner
	meal_category varchar(255), -- veg /non veg,beef etc
	cusine_type varchar(255), -- indian/american/japanese
	recipe varchar(255),
	calories integer,
	pic blob,
	primary key(id)
);
