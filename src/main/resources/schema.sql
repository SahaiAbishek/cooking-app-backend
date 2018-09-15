--create database cooking

create table meals
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

create table recipe
(
	RECIPE_ID integer auto_increment,
 	DESCRIPTION varchar(255),
	INGRADIENTS varchar(255), -- breakfast,lunch or dinner
	PREPRATION_INSTRUCTIONS varchar(255), -- veg /non veg,beef etc
	PIC blob,
	VIDEO VARCHAR(1000),
	MEAL_ID INTEGER,
	primary key(RECIPE_ID),
	INDEX `FK_RECIPE_meals` (`MEAL_ID`),
	CONSTRAINT `FK_RECIPE_meals` FOREIGN KEY (`MEAL_ID`) REFERENCES `meals` (`meal_id`)
);
