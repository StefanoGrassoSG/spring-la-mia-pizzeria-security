package org.java.spring.db.serv;

import java.util.List;

import org.java.spring.db.pojo.Ingredient;
import org.java.spring.db.pojo.Pizza;
import org.java.spring.db.repo.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	public List<Ingredient> findAll() {
		
		return ingredientRepository.findAll();
	}
	public Ingredient findById(int id) {
		
		return ingredientRepository.findById(id).get();
	}
	public List<Ingredient> findByName(String query) {
		
		return ingredientRepository.findByNameContainingIgnoreCase(query);
	}
	public void save(Ingredient ingredient) {
		
		ingredientRepository.save(ingredient);
	}
	public void delete(Ingredient ingredient) {
			
		ingredientRepository.delete(ingredient);
	}
}
