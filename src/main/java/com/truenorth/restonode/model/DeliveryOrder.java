package com.truenorth.restonode.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.maps.model.LatLng;

import lombok.Data;

@Data
@Entity
public class DeliveryOrder {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name="order_id")
	private List<Meal> meals = new ArrayList<>();

	private BigDecimal totalAmount;

	private String address;

	private LatLng destination;

}
