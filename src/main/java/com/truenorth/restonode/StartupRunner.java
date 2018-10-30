package com.truenorth.restonode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.truenorth.restonode.model.Rating;
import com.truenorth.restonode.model.Restaurant;
import com.truenorth.restonode.repository.RestaurantRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class StartupRunner {

	@Bean
	CommandLineRunner initDatabase(RestaurantRepository repository) {
		return args -> {
			Restaurant spiagge = new Restaurant("Spiagge Di Napoli", new LatLng(-34.6206867, -58.4155187), "abekerman@gmail.com");
			spiagge.addRating(new Rating(3));
			spiagge.addRating(new Rating(5));
			log.info("Preloading " + repository.save(spiagge));

			Restaurant donIgnacio = new Restaurant("Don Ignacio", new LatLng(-34.6106419, -58.4150649), "abekerman@gmail.com");
			donIgnacio.addRating(new Rating(4));
			log.info("Preloading " + repository.save(donIgnacio));
		};
	}

}