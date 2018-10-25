package com.cooking.repository;

import org.springframework.data.repository.CrudRepository;
import com.cooking.entity.ShoeEntity;

public interface ShoeRepo extends CrudRepository<ShoeEntity, Long> {
	@SuppressWarnings("unchecked")
	ShoeEntity save(ShoeEntity shoe);
}
