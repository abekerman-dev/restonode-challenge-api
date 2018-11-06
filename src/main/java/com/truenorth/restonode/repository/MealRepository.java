package com.truenorth.restonode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truenorth.restonode.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long> {

	@Query("select m from Meal m where m.id in :ids")
	List<Meal> findByIdList(List<Long> ids);

}
