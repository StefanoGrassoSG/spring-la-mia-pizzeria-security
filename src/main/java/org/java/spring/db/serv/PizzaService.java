package org.java.spring.db.serv;

import java.util.List;

import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.repo.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
	
	@Autowired
	private PizzaRepository pizzaRepository;
	
	public List<Pizza> findAll() {
		
		return pizzaRepository.findAll();
	}
	public Pizza findById(int id) {
		
		return pizzaRepository.findById(id).get();
	}
	public List<Pizza> findByName(String query) {
		
		return pizzaRepository.findByNameContainingIgnoreCase(query);
	}
	public void save(Pizza pizza) {
		
		pizzaRepository.save(pizza);
	}
	public void delete(Pizza pizza) {
			
		pizzaRepository.delete(pizza);
	}
}
