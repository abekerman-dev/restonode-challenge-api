package com.truenorth.restonode.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.model.LatLng;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class DeliveryOrder {

	@Id
	@GeneratedValue
	@ToString.Exclude
	@JsonIgnore
	private Long id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="order_id")
	private List<Meal> meals = new ArrayList<>();

	private BigDecimal totalAmount;

	// So that the restaurant will know where to deliver this order
	private String address;

	// Used only to calculate this order's ETA
	private LatLng destination;

}
