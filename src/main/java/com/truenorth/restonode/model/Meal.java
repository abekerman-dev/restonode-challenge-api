package com.truenorth.restonode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Meal {
	
	@Id
	@GeneratedValue
	@ToString.Exclude
	@JsonIgnore
	private Long id;

	private String name;
	
	private int qty;

	public Meal(String name, int qty) {
		super();
		this.name = name;
		this.qty = qty;
	}
	
}
