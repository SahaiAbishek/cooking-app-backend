package com.cooking.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cooking.entity.MealType;
import com.cooking.entity.MealsEntity;
import com.cooking.model.Meals;
import com.cooking.repository.MealsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CookingController {

	@Autowired
	MealsRepo mealsRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/food/items")
	@CrossOrigin
	public List<com.cooking.model.Meals> getAllFoodItems() throws Exception {
		List<com.cooking.model.Meals> targetList = new ArrayList<>();
		for (MealsEntity source : mealsRepo.findAll()) {
			Meals target = new Meals();
			BeanUtils.copyProperties(target, source);
			targetList.add(target);
		}
		return targetList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/{type}")
	@CrossOrigin
	public List<com.cooking.model.Meals> getFoodbyType(@PathVariable MealType type) throws Exception {
		List<MealsEntity> mealsList = mealsRepo.findByMealType(type.getMealtype());
		List<com.cooking.model.Meals> targetList = new ArrayList<>();
		for (MealsEntity source : mealsList) {
			Meals target = new Meals();
			BeanUtils.copyProperties(target, source);
			targetList.add(target);
		}
		return targetList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/item/ID/{id}")
	@CrossOrigin
	public com.cooking.model.Meals getFoodbyId(@PathVariable Long id) throws Exception {
		Optional<MealsEntity> meal = mealsRepo.findById(id);
		com.cooking.model.Meals target = new com.cooking.model.Meals();
		if (meal != null) {
			MealsEntity source = meal.get();
			BeanUtils.copyProperties(target, source);
			Base64.encodeBase64(source.getPic());
		}
		return target;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/food")
	@CrossOrigin
	public MealsEntity addfood(@RequestBody MealsEntity meal) {
		return mealsRepo.save(meal);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/food/item/name/{name}/")
	@CrossOrigin
	public String updateFood(@PathVariable String name, @RequestBody MealsEntity meal) {
		List<MealsEntity> meals = mealsRepo.findByName(name);
		if (meals == null || meals.size() == 0) {
			System.out.println("Nothing to update");
			return "Nothing to update";
		} else {
			meal.setId(meals.get(0).getId());
			MealsEntity resp = mealsRepo.save(meal);
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				return "Updated :" + objectMapper.writeValueAsString(resp);
			} catch (JsonProcessingException e) {
				return e.getMessage();
			}
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/food/ID/{id}/")
	@CrossOrigin
	public String updateFoodPic(@PathVariable Long id, @RequestParam("pic") MultipartFile pic) throws IOException {
		String retval = "";
		MealsEntity meal = mealsRepo.findById(new Long(id)).get();
		if (meal == null) {
			System.out.println("Nothing to update");
			return "Nothing to update";
		} else {
			meal.setPic(pic.getBytes());
			MealsEntity resp = mealsRepo.save(meal);
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				retval += objectMapper.writeValueAsString(resp);
			} catch (JsonProcessingException e) {
				return e.getMessage();
			}

			return "updated : " + retval;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/food/test")
	@CrossOrigin
	public String testMultipart(@RequestParam String name, @RequestParam String mealType, @RequestParam String recipe,
			@RequestParam String calories, @RequestParam("pic") MultipartFile pic) {
		MealsEntity meal = new MealsEntity();
		meal.setName(name);
		meal.setMealType(mealType);
		meal.setRecipe(recipe);
		meal.setCalories(new Long(calories));
		try {
			meal.setPic(pic.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		mealsRepo.save(meal);
		return name + " :  success";
	}

}
