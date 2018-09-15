--create database cooking

create table MEALS
(
	meal_id integer auto_increment,
 	name varchar(255),
	meal_type varchar(255), -- breakfast,lunch or dinner
	meal_category varchar(255), -- veg /non veg,beef etc
	cusine_type varchar(255), -- indian/american/japanese
	calories integer,
	pic blob,
	primary key(meal_id)
);

create table RECIPE
(
	RECIPE_ID integer auto_increment,
 	DESCRIPTION varchar(255),
	INGRADIENTS varchar(255), -- breakfast,lunch or dinner
	PREPRATION_INSTRUCTIONS varchar(255), -- veg /non veg,beef etc
	PIC blob,
	VIDEO VARCHAR(1000),
	MEAL_ID INTEGER,
	primary key(RECIPE_ID),
	FOREIGN KEY fk_meal_id(MEAL_ID)
	REFERENCES MEALS(MEAL_ID)
	ON UPDATE CASCADE
   	ON DELETE RESTRICT
);
