package com.cooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cooking.entity.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
	@SuppressWarnings("unchecked")
	UserEntity save(UserEntity user);
	List<UserEntity> findByEmail(String email);
	Optional<UserEntity> findById(Long id);
}
