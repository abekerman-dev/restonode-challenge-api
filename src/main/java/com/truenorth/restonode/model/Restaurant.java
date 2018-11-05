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
public class Restaurant implements Resource {

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

	@ToString.Exclude
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "restaurant_id")
	private List<Meal> meals = new ArrayList<>();

	public Restaurant(String name, LatLng location, String email) {
		this.name = name;
		this.location = location;
		this.email = email;
	}

	public void addRating(Rating rating) {
		ratings.add(rating);
	}
	
	public void addMeal(Meal meal) {
		meals.add(meal);
	}

	// Eclipse generated hashCode() and equals() below which override Lombok ones

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
