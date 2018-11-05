package com.truenorth.restonode.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Meal implements Resource {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	@JsonIgnore
	private Restaurant restaurant;

	private String name;

	private BigDecimal price;

	public Meal(Restaurant restaurant, String name, BigDecimal price) {
		super();
		this.restaurant = restaurant;
		this.name = name;
		this.price = price;
	}

}
