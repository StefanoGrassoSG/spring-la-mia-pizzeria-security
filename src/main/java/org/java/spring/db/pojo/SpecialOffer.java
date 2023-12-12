package org.java.spring.db.pojo;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="special_offer")
public class SpecialOffer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Column(length = 64, nullable = false)
	@Length(min = 3, max = 60, message = "title must be between 3 and 60 characters")
	@NotEmpty(message = "title can't be empty")
	private String title;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate startDate;
	private LocalDate endDate;
	
	@ManyToOne
	private Pizza pizza;
	
	public SpecialOffer() {}
	public SpecialOffer(String title, LocalDate startDate, LocalDate endDate, Pizza pizza) {
		setTitle(title);
		setStartDate(startDate);
		setEndDate(endDate);
		setPizza(pizza);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Pizza getPizza() {
		return pizza;
	}
	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}
	public void removePizza() {
	    if (pizza != null) {
	        pizza.getSpecialOffers().remove(this);
	        pizza = null;
	    }
	}
	
	@Override
	public String toString() {
		
		return "[" + getId() + "] " + getTitle() + " - " + getStartDate() + " - " + getEndDate();
	}
	
}
