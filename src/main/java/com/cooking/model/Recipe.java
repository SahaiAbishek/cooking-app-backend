package com.cooking.model;

import java.io.Serializable;

public class Recipe implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long recipeID;

	private String description;

	private String ingradients;

	private String preprationInstructions;

	private byte[] pic;

	private String video;

//	private Meals meal;

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

//	public Meals getMeal() {
//		return meal;
//	}
//
//	public void setMeal(Meals meal) {
//		this.meal = meal;
//	}

}
