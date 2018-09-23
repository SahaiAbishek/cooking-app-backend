package com.cooking.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooking.entity.MealsEntity;
import com.cooking.entity.UserEntity;
import com.cooking.repository.MealsRepo;
import com.cooking.repository.UserRepo;

@RestController
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private MealsRepo mealsRepo;

	@RequestMapping(method = RequestMethod.POST, path = "/user")
	@CrossOrigin
	public UserEntity saveUser(@RequestParam(required = true) String email, @RequestParam(required = true) String password) {
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
	public Boolean validateUser(@PathVariable String email, @PathVariable String password) {
		logger.info("Inside validateUser");
		password = DigestUtils.sha256Hex(password);
		try {
			List<UserEntity> users = userRepo.findByEmail(email);
			if (null != users && users.size() > 0) {
				for (UserEntity user : users) {
					if (password.equals(user.getPassword())) {
						return true;
					}
				}
			} else {
				logger.info("User found returning true");
				return false;
			}
		} catch (Exception ex) {
			logger.error("Exception in saving user");
			throw ex;
		}

		return false;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/user/favouret/{userId}/{mealId}")
	@CrossOrigin
	public UserEntity addUserMeal(@PathVariable long userId, @PathVariable long mealId) throws Exception {
		Optional<UserEntity> userOptional = userRepo.findById(userId);
		UserEntity user = userOptional.get();
		Optional<MealsEntity> mealOptional = mealsRepo.findById(mealId);
		MealsEntity meal =mealOptional.get();
		Set<MealsEntity> mealSet = new HashSet<>();
		mealSet.add(meal);
		user.setMeals(mealSet);
		UserEntity retUser = userRepo.save(user);
		return retUser;
	}
}
