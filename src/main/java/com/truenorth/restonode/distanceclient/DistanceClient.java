package com.truenorth.restonode.distanceclient;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistanceClient {

	private static final String GOOGLE_API_KEY = "AIzaSyCpdWlCVR_vz2LiH42OSJnMEIhDjntdeoA";

	public static long calculateETA(LatLng origin, LatLng destination) {
		GeoApiContext context = new GeoApiContext.Builder().apiKey(GOOGLE_API_KEY).build();
		DistanceMatrix distanceMatrix = null;
		Duration duration = null;
		try {
			distanceMatrix = DistanceMatrixApi.newRequest(context).origins(origin).destinations(destination)
					.await();
			duration = distanceMatrix.rows[0].elements[0].duration;
			log.info("ETA between " + origin + " and " + destination + " is " + duration.inSeconds + " seconds ("
					+ duration.humanReadable + ")");
		} catch (ApiException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return duration.inSeconds;
	}

}
