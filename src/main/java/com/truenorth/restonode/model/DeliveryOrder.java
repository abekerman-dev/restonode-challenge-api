package com.truenorth.restonode.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.google.maps.model.LatLng;

import lombok.Data;

@Data
@Entity
public class DeliveryOrder {

	@Id
	@GeneratedValue
	private Long id;

	private String eta;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "order_meal", joinColumns = { @JoinColumn(name = "order_id") }, inverseJoinColumns = {
			@JoinColumn(name = "meal_id") })
	private List<Meal> meals = new ArrayList<>();

	private BigDecimal totalAmount;

	// So that the restaurant will know where to deliver this order
	private String address;

	// Used only to calculate this order's ETA
	private LatLng destination;

	private LocalDateTime timestamp;

	public DeliveryOrder(List<Meal> meals, String address, LatLng destination) {
		super();
		this.meals = meals;
		this.address = address;
		this.destination = destination;
	}
	
	/**
	 * Returns the restaurant this DeliveryOrder belongs to
	 * 
	 * @param order an order about to be created with a non-empty list of meals
	 * @return
	 */
	public Restaurant getRestaurant() {
		return this.getMeals().get(0).getRestaurant();
	}


}
