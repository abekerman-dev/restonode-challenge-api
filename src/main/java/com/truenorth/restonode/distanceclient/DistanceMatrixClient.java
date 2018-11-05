package com.truenorth.restonode.distanceclient;

import com.google.maps.model.LatLng;

public interface DistanceMatrixClient {

	public String calculateETA(LatLng origin, LatLng destination) throws Exception;
	
}
