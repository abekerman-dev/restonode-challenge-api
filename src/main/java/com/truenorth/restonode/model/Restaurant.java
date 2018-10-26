package com.truenorth.restonode.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.maps.model.LatLng;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Restaurant {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	private LatLng location;

//	@ToString.Exclude
//	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "restaurant_id")
	private List<Rating> ratings = new ArrayList<>();

	public Restaurant(String name, LatLng location) {
		this.name = name;
		this.location = location;
	}

	public void addRating(Rating rating) {
		ratings.add(rating);
	}

}
