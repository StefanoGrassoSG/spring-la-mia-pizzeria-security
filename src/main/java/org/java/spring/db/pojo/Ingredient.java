package org.java.spring.db.pojo;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 64, nullable = false, unique = true)
	@Length(min = 3, max = 60, message = "name must be between 3 and 60 characters")
	@NotEmpty(message = "name can't be empty")
	private String name;
	
	@ManyToMany(mappedBy = "ingredients")
	private List<Pizza> pizzas;
	
	public Ingredient() {}
	public Ingredient(String name) {
		setName(name);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Pizza> getPizzas() {
		return pizzas;
	}
	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}
	public void clearPizzas() {
	    if (pizzas != null) {
	        for (Pizza pizza : pizzas) {
	            pizza.getIngredients().remove(this);
	        }
	        pizzas.clear();
	    }
	}

	
	@Override
	public String toString() {
		
		return "[" + getId() + "] " + getName();
	}
}
	