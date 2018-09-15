package com.cooking.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MEALS")
public class MealsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_id")
	@JsonIgnore
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "meal_type")
	private String mealType;

	@Column(name = "meal_category")
	private String mealCategory;

	@Column(name = "cusine_type")
	private String cusineType;

	@Column(name = "calories")
	private Long calories;

	@Lob
	@Column(name = "pic")
	private byte[] pic;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="MEAL_ID")
	private Set<RecipeEntity> recipes;

	public Set<RecipeEntity> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<RecipeEntity> recipes) {
		this.recipes = recipes;
	}

	public String getMealCategory() {
		return mealCategory;
	}

	public void setMealCategory(String mealCategory) {
		this.mealCategory = mealCategory;
	}

	public String getCusineType() {
		return cusineType;
	}

	public void setCusineType(String cusineType) {
		this.cusineType = cusineType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public Long getCalories() {
		return calories;
	}

	public void setCalories(Long calories) {
		this.calories = calories;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

}
