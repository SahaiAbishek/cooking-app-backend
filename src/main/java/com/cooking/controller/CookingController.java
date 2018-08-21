package com.cooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooking.entity.MealType;
import com.cooking.entity.Meals;
import com.cooking.repository.MealsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CookingController {

	@Autowired
	MealsRepo mealsRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/food/items")
	@CrossOrigin
	public List<Meals> getAllFoodItems() {
		return mealsRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/{type}")
	@CrossOrigin
	public List<Meals> getFoodbyType(@PathVariable MealType type) {
		return mealsRepo.findByMealType(type.getMealtype());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/food")
	@CrossOrigin
	public Meals addfood(@RequestBody Meals meal) {
		return mealsRepo.save(meal);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/food/{name}/")
	@CrossOrigin
	public String updateFood(@PathVariable String name, @RequestBody Meals meal) {
		List<Meals> meals = mealsRepo.findByName(name);
		if (meals == null || meals.size() == 0) {
			System.out.println("Nothing to update");
			return "Nothing to update";
		} else {
			meal.setId(meals.get(0).getId());
			Meals resp = mealsRepo.save(meal);
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				return "Updated :" + objectMapper.writeValueAsString(resp);
			} catch (JsonProcessingException e) {
				return e.getMessage();
			}
		}
	}

}
