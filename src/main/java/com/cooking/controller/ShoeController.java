package com.cooking.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cooking.entity.ShoeEntity;
import com.cooking.entity.UserEntity;
import com.cooking.repository.ShoeRepo;
import com.cooking.repository.UserRepo;

@RestController
public class ShoeController {
	
	Logger logger = LoggerFactory.getLogger(ShoeController.class);
	
	@Autowired
	private ShoeRepo shoeRepo;
	
	@Autowired
	private UserRepo userRepo;

	@RequestMapping(method = RequestMethod.POST, path = "/user/shoe", produces = "application/json")
	@CrossOrigin
	public boolean addShoe(@RequestParam(required = false) String brand,
			@RequestParam(required = false) String model, 
			@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-mm-dd") Date startDate,
			@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-mm-dd") Date endDate,
			@RequestParam(required = false) Long miles,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "userID", required = false) Long userId
			) throws Exception {
		logger.debug("Inside addShoe..");
		Optional<UserEntity> optionalUser = userRepo.findById(userId);
		if(null != optionalUser){
			UserEntity user = optionalUser.get();
			Set<UserEntity> userSet = new HashSet<>();
			userSet.add(user);
			ShoeEntity shoeEntity = new ShoeEntity();
			shoeEntity.setBrand(brand);
			shoeEntity.setModel(model);
			shoeEntity.setStartDate(startDate);
			shoeEntity.setEndDate(endDate);
			shoeEntity.setMiles(miles);
			if(null != file){
				shoeEntity.setPic(file.getBytes());
			}
			shoeEntity.setUser(userSet);
			logger.debug("Trying to insert..");
			shoeRepo.save(shoeEntity);
			logger.debug("Inserted successfully.");
			return true;
		}
		
		
		return false;
	}
}
