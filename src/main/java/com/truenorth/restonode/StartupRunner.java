package com.truenorth.restonode;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.truenorth.restonode.model.Meal;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.MealRepository;
import com.truenorth.restonode.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class StartupRunner {

	@Bean
	CommandLineRunner initDatabase(RestaurantRepository restaurantRepo, MealRepository mealRepo) {
		return args -> {
			Restaurant spiagge = new Restaurant("Spiagge Di Napoli", new LatLng(-34.6206867, -58.4155187), "abekerman@gmail.com");
			spiagge.addRating(new Rating(3));
			spiagge.addRating(new Rating(5));
			log.info("Preloading " + restaurantRepo.save(spiagge));

			Meal fucileFierrito = new Meal(spiagge, "Fucile al fierrito, el clásico de la casa!", BigDecimal.valueOf(200));
			log.info("Preloading " + mealRepo.save(fucileFierrito));
			Meal rabas = new Meal(spiagge, "Rabas a la romana, la vedette de la casa!", BigDecimal.valueOf(275));
			log.info("Preloading " + mealRepo.save(rabas));

			Restaurant donIgnacio = new Restaurant("Don Ignacio", new LatLng(-34.6106419, -58.4150649), "abekerman@gmail.com");
			donIgnacio.addRating(new Rating(4));
			log.info("Preloading " + restaurantRepo.save(donIgnacio));
			
			Meal milanga = new Meal(donIgnacio, "Mila napo, la que no puede faltar!", BigDecimal.valueOf(180));
			log.info("Preloading " + mealRepo.save(milanga));

			
		};
	}

}