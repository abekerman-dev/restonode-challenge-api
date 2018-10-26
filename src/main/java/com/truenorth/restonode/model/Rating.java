package com.truenorth.restonode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Rating {

	@Id
	@GeneratedValue
	@ToString.Exclude
	@JsonIgnore
	private Long id;

	private int rating;

	public Rating(int rating) {
		this.rating = rating;
	}

}
