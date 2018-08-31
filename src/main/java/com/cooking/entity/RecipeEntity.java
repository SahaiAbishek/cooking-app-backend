package com.cooking.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RECIPE")
public class RecipeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECIPE_ID")
	@JsonIgnore
	private Long recipeID;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "INGRADIENTS")
	private String ingradients;

	@Column(name = "PREPRATION_INSTRUCTIONS")
	private String preprationInstructions;

	@Lob
	@Column(name = "PIC")
	private byte[] pic;

	@Column(name = "VIDEO")
	private String video;

//	@ManyToOne
//	@JoinColumn(name = "MEAL_ID")
//	private MealsEntity meal;

	public Long getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(Long recipeID) {
		this.recipeID = recipeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIngradients() {
		return ingradients;
	}

	public void setIngradients(String ingradients) {
		this.ingradients = ingradients;
	}

	public String getPreprationInstructions() {
		return preprationInstructions;
	}

	public void setPreprationInstructions(String preprationInstructions) {
		this.preprationInstructions = preprationInstructions;
	}

	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

//	public MealsEntity getMeal() {
//		return meal;
//	}
//
//	public void setMeal(MealsEntity meal) {
//		this.meal = meal;
//	}

}
