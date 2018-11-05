package com.truenorth.restonode.dto;

import java.util.List;

import com.google.maps.model.LatLng;

import lombok.Data;

@Data
public class DeliveryOrderRestRequestBody {

	private List<Long> mealIds;
	
	private String address;
	
	private LatLng destination;
}
