package com.cooking.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cooking.entity.Meals;
import com.cooking.repository.MealsRepo;

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
	public List<Meals> getFoodbyType(@PathVariable String type ) {
		return mealsRepo.findByMealType(type);
	}

}
