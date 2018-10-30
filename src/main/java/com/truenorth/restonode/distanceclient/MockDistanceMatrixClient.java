package com.truenorth.restonode.distanceclient;

import org.springframework.stereotype.Component;

import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

@Component
public class MockDistanceMatrixClient implements DistanceMatrixClient {

	@Override
	public Duration calculateDuration(LatLng origin, LatLng destination) throws Exception {
		Duration duration = new Duration();
		duration.inSeconds = 1800;
		duration.humanReadable = "30 minutes";
		
		return duration;
	}

}
