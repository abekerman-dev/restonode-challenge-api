package com.truenorth.restonode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truenorth.restonode.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

	@Query("select m from Meal m where m.id in :ids")
	List<Meal> findByIdList(List<Long> ids);

	@Query("select m from Meal m join m.restaurant restaurant on restaurant.id = ?1")
	List<Meal> findByRestaurantId(Long restaurantId);
}
