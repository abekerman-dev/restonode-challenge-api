package com.truenorth.restonode.distanceclient;

import org.springframework.stereotype.Component;

import com.google.maps.model.LatLng;
import com.truenorth.restonode.exception.UnfinishedTransactionException;

@Component
public class MockDistanceMatrixClient implements DistanceMatrixClient {

	@Override
	public String calculateETA(LatLng origin, LatLng destination) throws UnfinishedTransactionException {
		return "[mock ETA] 30 minutes";
	}

}
