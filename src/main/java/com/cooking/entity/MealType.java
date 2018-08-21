package com.cooking.entity;

public enum MealType {
	BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    SNACK("snack");
	
	private String mealType;
	 
	MealType(String mealType) {
        this.mealType = mealType;
    }
 
    public String getMealtype() {
        return mealType;
    }
}
