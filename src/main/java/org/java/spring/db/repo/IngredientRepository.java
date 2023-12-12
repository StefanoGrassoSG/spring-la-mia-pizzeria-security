package org.java.spring.db.repo;

import java.util.List;

import org.java.spring.db.pojo.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{

	List<Ingredient> findByNameContainingIgnoreCase(String query);

}
