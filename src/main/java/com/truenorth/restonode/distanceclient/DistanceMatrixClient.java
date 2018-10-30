package com.truenorth.restonode.distanceclient;

import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

public interface DistanceMatrixClient {

	public Duration calculateDuration(LatLng origin, LatLng destination) throws Exception;
	
}
