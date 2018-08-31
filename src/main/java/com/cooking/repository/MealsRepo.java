package com.cooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cooking.entity.MealsEntity;

public interface MealsRepo extends CrudRepository<MealsEntity,Long> {
	List<MealsEntity> findAll();
	List<MealsEntity> findByMealType(String type);
	List<MealsEntity> findByNameContainingIgnoreCase(String name);
	Optional<MealsEntity> findById(Long id);
	@SuppressWarnings("unchecked")
	MealsEntity save(MealsEntity meal);
}
