package org.java.spring.db.pojo;

import java.util.Arrays;
import java.util.List; 

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="pizza")
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 64, nullable = false, unique = true)
	@Length(min = 3, max = 60, message = "name must be between 3 and 60 characters")
	@NotEmpty(message = "name can't be empty")
	private String name;
	
	@Column(columnDefinition = "TEXT")
	@Length(min = 3, max = 1500, message = "description must be between 3 and 1500 characters")
	@NotEmpty(message = "description can't be empty")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String image;
	
	@NotNull(message = "price can't be empty")
	@Min(value = 1, message = "the price must be greater than 0")
	@Max(value = 100, message = "the price must be lower than 100")
	private double price;
	
	@OneToMany(mappedBy = "pizza",  cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SpecialOffer> specialOffers;
	
	@ManyToMany
	private List<Ingredient> ingredients;
	
	public Pizza() {}
	public Pizza(String name, String description, String image, double price, Ingredient... ingredients) {
		setName(name);
		setDescription(description);
		setImage(image);
		setPrice(price);
		setIngredients(ingredients);
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<SpecialOffer> getSpecialOffers() {
		return specialOffers;
	}
	public void setSpecialOffers(List<SpecialOffer> specialOffers) {
		this.specialOffers = specialOffers;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public void setIngredients(Ingredient... ingredients) {	
		setIngredients(Arrays.asList(ingredients));
	}
	public void clearIngredients() {
	    if (ingredients != null) {
	        for (Ingredient ing : ingredients) {
	            ing.getPizzas().remove(this);
	        }
	        ingredients.clear();
	    }
	}
	
	
	@Override
	public String toString() {
		
		return "[" + getId() + "] " + getName() + " - " 
				+ getDescription() + getImage() + " - "
				+ getPrice() + getSpecialOffers();
	}
}
