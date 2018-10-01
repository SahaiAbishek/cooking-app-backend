package com.cooking.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.cooking.entity.MealPlanEntity;

@Transactional
public interface MealPlanRepo extends CrudRepository<MealPlanEntity, Long>{
}
