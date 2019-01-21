package com.cooking.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cooking.entity.ShoeEntity;
import com.cooking.entity.UserEntity;
import com.cooking.model.Shoe;
import com.cooking.model.User;
import com.cooking.repository.UserRepo;

import io.swagger.annotations.ApiOperation;

@RestController
public class ShoeController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepo userRepo;

	@ApiOperation(value="Add new shoe")
	@RequestMapping(method = RequestMethod.POST, path = "/shoe")
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

	@ApiOperation(value="Get shoes for the user")
	@RequestMapping(method = RequestMethod.GET, path = "/shoes/{email}", produces = "application/json")
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
