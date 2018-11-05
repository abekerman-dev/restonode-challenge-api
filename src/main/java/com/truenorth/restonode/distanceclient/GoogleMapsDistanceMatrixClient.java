package com.truenorth.restonode.distanceclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GoogleMapsDistanceMatrixClient implements DistanceMatrixClient {

	@Value("${DISTANCE_MATRIX_API_KEY}")
	private String apiKey;

	/**
	 * Invokes google maps DistanceMatrix API in order to retrieve the ETA given an
	 * origin and a destination
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 * @throws Exception
	 */
	public String calculateETA(LatLng origin, LatLng destination) throws Exception {
		log.debug("google apiKey=" + apiKey);
		GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
		DistanceMatrix distanceMatrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination)
				.await();
		Duration duration = distanceMatrix.rows[0].elements[0].duration;
		log.debug("ETA between " + origin + " and " + destination + " is " + duration);

		return duration.humanReadable;
	}

}
