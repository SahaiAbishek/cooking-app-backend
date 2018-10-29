package com.cooking.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.cooking.entity.MealPlanEntity;
import com.cooking.entity.MealsEntity;
import com.cooking.entity.ShoeEntity;
import com.cooking.entity.UserEntity;
import com.cooking.model.Shoe;
import com.cooking.model.User;
import com.cooking.repository.MealPlanRepo;
import com.cooking.repository.MealsRepo;
import com.cooking.repository.UserRepo;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MealsRepo mealsRepo;

	@Autowired
	private MealPlanRepo mealPlanRepo;

	@RequestMapping(method = RequestMethod.POST, path = "/user/add")
	@CrossOrigin
	public UserEntity addUser(@RequestBody User user) throws Exception {
		if (null != user) {
			UserEntity userEntity = new UserEntity();
			if (user.getEmail() != null) {
				userEntity.setEmail(user.getEmail());
			}
			if (user.getPassword() != null) {
				userEntity.setPassword(DigestUtils.sha256Hex(user.getPassword()));
			}
			UserEntity retuser = null;
			try {
				retuser = userRepo.save(userEntity);
			} catch (Exception ex) {
				logger.error("Exception in saving user");
				throw ex;
			}
			return retuser;
		} else {
			throw new Exception("Please enter login details");
		}

	}

	@RequestMapping(method = RequestMethod.POST, path = "/user")
	@CrossOrigin
	public UserEntity saveUser(@RequestParam(required = true) String email,
			@RequestParam(required = true) String password) {
		UserEntity user = new UserEntity();
		user.setEmail(email);
		user.setPassword(DigestUtils.sha256Hex(password));
		UserEntity retuser = null;
		try {
			retuser = userRepo.save(user);
		} catch (Exception ex) {
			logger.error("Exception in saving user");
			throw ex;
		}

		return retuser;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/{email}/{password}")
	@CrossOrigin
	public String validateUser(@PathVariable String email, @PathVariable String password) {
		logger.info("Inside validateUser " + email + " -- " + password);
		password = DigestUtils.sha256Hex(password);
		try {
			List<UserEntity> users = userRepo.findByEmail(email);
			if (null != users && users.size() > 0) {
				for (UserEntity user : users) {
					if (password.equals(user.getPassword())) {
						logger.info("User found returning true");
						return "true";
					}
				}
			} else {
				logger.info("User not found returning false");
				return "false";
			}
		} catch (Exception ex) {
			logger.error("Exception in saving user");
			throw ex;
		}
		logger.info("User not found returning false");
		return "false";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/favouret/{userId}/{mealId}")
	@CrossOrigin
	public UserEntity addUserMeal(@PathVariable long userId, @PathVariable long mealId) throws Exception {
		Optional<UserEntity> userOptional = userRepo.findById(userId);
		UserEntity user = userOptional.get();
		Optional<MealsEntity> mealOptional = mealsRepo.findById(mealId);
		MealsEntity meal = mealOptional.get();
		Set<MealsEntity> mealSet = new HashSet<>();
		mealSet.add(meal);
		user.setMeals(mealSet);
		UserEntity retUser = userRepo.save(user);
		return retUser;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/user/meal-plan")
	@CrossOrigin
	public boolean userMealPlan(@RequestBody List<MealPlanEntity> mealPlans) throws Exception {
		logger.info("Inside userMealPlan");
		for (MealPlanEntity mealPlan : mealPlans) {
			try {
				mealPlanRepo.save(mealPlan);
			} catch (Exception ex) {
				logger.error("Exception in saving meal plan" + ex.getMessage());
				throw new Exception(ex.getMessage());
			}
		}
		logger.info("Saved all successfully");
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/user/shoe")
	@CrossOrigin
	public ResponseEntity<User> addShoe(@RequestParam(required = false) String brand,
			@RequestParam(required = false) String model,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-mm-dd") Date startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-mm-dd") Date endDate,
			@RequestParam(required = false) Long miles, @RequestParam(required = false) String size,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "email", required = false) String email) throws Exception {
		logger.debug("Inside addShoe..");
		List<UserEntity> users = userRepo.findByEmail(email);
		if (null != users) {
			for (UserEntity user : users) {
				ShoeEntity shoeEntity = new ShoeEntity();
				if (null != brand)
					shoeEntity.setBrand(brand);
				if (null != model)
					shoeEntity.setModel(model);
				if (null != startDate)
					shoeEntity.setStartDate(startDate);
				if (null != endDate)
					shoeEntity.setEndDate(endDate);
				if (null != miles)
					shoeEntity.setMiles(miles);
				if (null != file) {
					shoeEntity.setPic(file.getBytes());
				}
				shoeEntity.setUser(user);
				Set<ShoeEntity> shoeEntities = new HashSet<>();
				shoeEntities.add(shoeEntity);
				user.setShoes(shoeEntities);
				logger.debug("Trying to insert..");
				UserEntity sourceUser = userRepo.save(user);
				logger.debug("Inserted successfully." + sourceUser);
				User targetUser = new User();
				BeanUtils.copyProperties(targetUser, sourceUser);
				return new ResponseEntity<>(targetUser, HttpStatus.OK);
			}

		}

		return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user/shoes/{email}", produces = "application/json")
	@CrossOrigin
	public ResponseEntity<Set<Shoe>> getShoesByUserEmail(@PathVariable String email) throws Exception {
		logger.debug("Inside getShoesByUserId..");
		List<UserEntity> users = userRepo.findByEmail(email);
		if (null != users) {
			for (UserEntity userentity : users) {
				Set<ShoeEntity> shoeEntities = userentity.getShoes();
				Set<Shoe> shoes = new HashSet<>();
				if (null != shoeEntities) {
					for (ShoeEntity sourceShoe : shoeEntities) {
						Shoe targetshoe = new Shoe();
						BeanUtils.copyProperties(targetshoe, sourceShoe);
						shoes.add(targetshoe);
					}
					return new ResponseEntity<Set<Shoe>>(shoes, HttpStatus.OK);
				}
			}
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
