package com.cooking.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooking.entity.UserEntity;
import com.cooking.model.User;
import com.cooking.repository.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepo userRepo;

	@ApiOperation(value = "Register new user")
	@PostMapping(path = "/user/register")
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

	@ApiOperation(value = "Validate user login details")
	@PostMapping(path = "/user/login")
	@CrossOrigin
	public String validateUser(@RequestBody User usr) {
		String email = usr.getEmail();
		String password = usr.getPassword();
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

}
