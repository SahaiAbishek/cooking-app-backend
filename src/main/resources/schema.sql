--create database cooking
create table app_users
(
	user_id integer auto_increment,
 	email varchar(1000),
	password text, 
	primary key(user_id)
);

create table user_meals
(
	user_id integer,
	meal_id integer
);

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
--	,INDEX `FK_RECIPE_meals` (`MEAL_ID`),
	FOREIGN KEY (`MEAL_ID`) REFERENCES `meals` (`meal_id`)
);

create table meal_plan
(
	id integer auto_increment,
 	day_of_week varchar(255),
	meal_type varchar(255), 
	meal_name varchar(999), 
	user_id int,
	primary key(id),
--	INDEX `fk_user` (`user_id`),
	FOREIGN KEY (`user_id`) REFERENCES `app_users` (`user_id`)
);

create table user_shoes
(
	id integer auto_increment,
 	brand varchar(255),
	model varchar(255),
	start_date date,
	end_date date,
	miles integer,
	pic blob,
	user_id int,
	primary key(id),
--	INDEX `fk_user` (`user_id`),
	FOREIGN KEY (`user_id`) REFERENCES `app_users` (`user_id`)
);
