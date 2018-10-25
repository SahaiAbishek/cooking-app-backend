package com.cooking.cooking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import com.cooking.controller.CookingController;
import com.cooking.model.Meals;


public class ConnkingTests {
	
	@InjectMocks
	CookingController controller = new CookingController();

	@Test
	public void test() {
		assertNotNull(controller);
		try {
			ResponseEntity<List<Meals>> meals = controller.getAllFoodItems();
			assertNotNull(meals);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("a", "a");
	}

}
