package com.truenorth.restonode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truenorth.restonode.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	@Query("select r from Restaurant r join r.ratings ratings on ratings.rating = ?1")
	List<Restaurant> findByRating(int rating);
	
}
