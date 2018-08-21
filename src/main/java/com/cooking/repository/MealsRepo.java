package com.cooking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cooking.entity.Meals;

public interface MealsRepo extends CrudRepository<Meals,Long> {
	List<Meals> findAll();
	List<Meals> findByMealType(String type);
	@SuppressWarnings("unchecked")
	Meals save(Meals meal);
}
