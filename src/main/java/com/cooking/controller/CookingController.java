package com.cooking.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cooking.entity.RecipeEntity;
import com.cooking.model.Meals;
import com.cooking.model.Recipe;
import com.cooking.repository.MealsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CookingController {

	Logger logger = LoggerFactory.getLogger(CookingController.class);

	@Autowired
	MealsRepo mealsRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/food/items", produces = "application/json")
	@CrossOrigin
	public ResponseEntity<List<com.cooking.model.Meals>> getAllFoodItems() throws Exception {
		logger.info("Inside getAllFoodItems");
		List<com.cooking.model.Meals> targetList = new ArrayList<>();
		List<MealsEntity> meals = null;
		try {
			meals = mealsRepo.findAll();
		} catch (Exception ex) {
			logger.error("Exception in finding all records");
			throw new Exception(ex.getMessage());
		}
		for (MealsEntity source : meals) {
			Meals target = new Meals();
			BeanUtils.copyProperties(target, source);
			target.setRecipes(null);
			Set<Recipe> targetRecipes = new HashSet<>();
			for (RecipeEntity sourceRecipe : source.getRecipes()) {
				Recipe targetRecipe = new Recipe();
				BeanUtils.copyProperties(targetRecipe, sourceRecipe);
				targetRecipes.add(targetRecipe);
			}
			target.setRecipes(targetRecipes);
			targetList.add(target);
		}
		return new ResponseEntity<List<Meals>>(targetList, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/items/{name}")
	@CrossOrigin
	public List<com.cooking.model.Meals> getFoodByName(@PathVariable String name) throws Exception {
		logger.info("Inside getFoodByName");
		List<com.cooking.model.Meals> targetList = new ArrayList<>();
		for (MealsEntity source : mealsRepo.findByNameContainingIgnoreCase(name)) {
			Meals target = new Meals();
			BeanUtils.copyProperties(target, source);
			target.setRecipes(null);
			Set<Recipe> targetRecipes = new HashSet<>();
			for (RecipeEntity sourceRecipe : source.getRecipes()) {
				Recipe targetRecipe = new Recipe();
				BeanUtils.copyProperties(targetRecipe, sourceRecipe);
				targetRecipes.add(targetRecipe);
			}
			target.setRecipes(targetRecipes);
			targetList.add(target);

		}
		return targetList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/{type}")
	@CrossOrigin
	public List<com.cooking.model.Meals> getFoodbyType(@PathVariable MealType type) throws Exception {
		logger.info("Inside getFoodbyType");
		List<MealsEntity> mealsList = mealsRepo.findByMealType(type.getMealtype());
		List<com.cooking.model.Meals> targetList = new ArrayList<>();
		for (MealsEntity source : mealsList) {
			Meals target = new Meals();
			BeanUtils.copyProperties(target, source);
			target.setRecipes(null);
			Set<Recipe> targetRecipes = new HashSet<>();
			for (RecipeEntity sourceRecipe : source.getRecipes()) {
				Recipe targetRecipe = new Recipe();
				BeanUtils.copyProperties(targetRecipe, sourceRecipe);
				targetRecipes.add(targetRecipe);
			}
			target.setRecipes(targetRecipes);
			targetList.add(target);

		}
		return targetList;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/food/item/ID/{id}")
	@CrossOrigin
	public com.cooking.model.Meals getFoodbyId(@PathVariable Long id) throws Exception {
		logger.info("Inside getFoodbyId");
		Optional<MealsEntity> meal = mealsRepo.findById(id);
		com.cooking.model.Meals target = new com.cooking.model.Meals();
		if (meal != null) {
			MealsEntity source = meal.get();
			BeanUtils.copyProperties(target, source);
			target.setRecipes(null);
			Set<Recipe> targetRecipes = new HashSet<>();
			for (RecipeEntity sourceRecipe : source.getRecipes()) {
				Recipe targetRecipe = new Recipe();
				BeanUtils.copyProperties(targetRecipe, sourceRecipe);
				targetRecipes.add(targetRecipe);
			}
			target.setRecipes(targetRecipes);
			// Base64.encodeBase64(source.getPic());
		}
		return target;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/food", headers = "content-type=multipart/form-data,application/octet-stream,application/x-www-form-urlencoded", consumes = {
			"application/x-www-form-urlencoded" })
	@CrossOrigin
	public MealsEntity addFood(@RequestParam(required = false) String name,
			@RequestParam(required = false) String mealCategory, @RequestParam(required = false) String mealType,
			@RequestParam(required = false) String calories, @RequestParam(required = false) String cusineType,
			@RequestParam(required = false) String recipeDescription,
			@RequestParam(required = false) String recipeIngradients,
			@RequestParam(required = false) String recipePreprationInstructions,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		logger.info("Inside addFood");
		MealsEntity meal = new MealsEntity();
		meal.setName(name);
		if (null != calories)
			meal.setCalories(new Long(calories));
		if (null != cusineType)
			meal.setCusineType(cusineType);
		if (null != mealCategory)
			meal.setMealCategory(mealCategory);
		if (null != mealType)
			meal.setMealType(mealType);

		if ((null != recipeDescription) || (null != recipeIngradients) || (null != recipePreprationInstructions)) {
			Set<RecipeEntity> recipes = new HashSet<>();
			RecipeEntity recipe = new RecipeEntity();
			if (null != recipeDescription) {
				recipe.setDescription(recipeDescription);
			}
			if (null != recipeIngradients) {
				recipe.setIngradients(recipeIngradients);
			}
			if (null != recipePreprationInstructions) {
				recipe.setPreprationInstructions(recipePreprationInstructions);
			}
			recipes.add(recipe);
			meal.setRecipes(recipes);
		}

		try {
			if (null != file)
				meal.setPic(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("multopart error on controller");
		}

		return mealsRepo.save(meal);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/food/item/name/{name}/", headers = "content-type=multipart/form-data,application/octet-stream,application/x-www-form-urlencoded", consumes = {
			"application/x-www-form-urlencoded" })
	@CrossOrigin
	public String updateFood(@PathVariable String name, @RequestBody MealsEntity meal) {
		logger.info("Inside updateFood");
		List<MealsEntity> meals = mealsRepo.findByNameContainingIgnoreCase(name);
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
	public String updateFoodPic(@PathVariable Long id, @RequestParam("pic") MultipartFile pic) throws Exception {
		logger.info("Inside updateFoodPic");
		MealsEntity sourceMeal = mealsRepo.findById(new Long(id)).get();
		if (sourceMeal == null) {
			System.out.println("Nothing to update");
			return "Nothing to update";
		} else {
			MealsEntity destMeal = new MealsEntity();
			try {
				BeanUtils.copyProperties(destMeal, sourceMeal);
				destMeal.setRecipes(null);
				Set<RecipeEntity> targetRecipes = new HashSet<>();
				for (RecipeEntity sourceRecipe : sourceMeal.getRecipes()) {
					RecipeEntity targetRecipe = new RecipeEntity();
					BeanUtils.copyProperties(targetRecipe, sourceRecipe);
					targetRecipes.add(sourceRecipe);
				}
				destMeal.setRecipes(targetRecipes);
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				throw new Exception(e1.getMessage());
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
				throw new Exception(e1.getMessage());
			}
			destMeal.setPic(pic.getBytes());
			MealsEntity resp = mealsRepo.save(destMeal);
			if (null != resp)
				return "updated : ";
			else
				return "failure";
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/food/item/{id}", headers = "content-type=multipart/form-data,application/octet-stream,application/x-www-form-urlencoded", consumes = {
			"application/x-www-form-urlencoded" })
	@CrossOrigin
	public MealsEntity updateFood(@PathVariable Long id, @RequestParam(required = false) String name,
			@RequestParam(required = false) String mealCategory, @RequestParam(required = false) String mealType,
			@RequestParam(required = false) String calories, @RequestParam(required = false) String cusineType,
			@RequestParam(required = false) String recipeDescription,
			@RequestParam(required = false) String recipeIngradients,
			@RequestParam(required = false) String recipePreprationInstructions,
			@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		logger.info("Inside updateFood");
		MealsEntity meal = new MealsEntity();
		meal.setId(id);
		if (null != name)
			meal.setName(name);
		if (null != calories && calories.length() > 0)
			meal.setCalories(new Long(calories));
		if (null != cusineType)
			meal.setCusineType(cusineType);
		if (null != mealCategory)
			meal.setMealCategory(mealCategory);
		if (null != mealType)
			meal.setMealType(mealType);

		if ((null != recipeDescription) || (null != recipeIngradients) || (null != recipePreprationInstructions)) {
			Set<RecipeEntity> recipes = new HashSet<>();
			RecipeEntity recipe = new RecipeEntity();
			if (null != recipeDescription) {
				recipe.setDescription(recipeDescription);
			}
			if (null != recipeIngradients) {
				recipe.setIngradients(recipeIngradients);
			}
			if (null != recipePreprationInstructions) {
				recipe.setPreprationInstructions(recipePreprationInstructions);
			}
			recipes.add(recipe);
			meal.setRecipes(recipes);
		}

		try {
			if (null != file)
				meal.setPic(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("multopart error on controller");
		}

		return mealsRepo.save(meal);
	}

}
