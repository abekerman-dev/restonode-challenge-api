package com.truenorth.restonode.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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
	
	private String email;

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id")
	private List<Rating> ratings = new ArrayList<>();

	public Restaurant(String name, LatLng location, String email) {
		this.name = name;
		this.location = location;
		this.email = email;
	}

	public void addRating(Rating rating) {
		ratings.add(rating);
	}

}
